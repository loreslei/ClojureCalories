(ns app-clojure.food-calories
  (:require
   [app-clojure.tradutor :refer [capitalizar traduzir-pt-en]]
   [app-clojure.nutrition :refer [buscar-alimento]]
   [clj-time.core :as t]
   [clj-time.format :as f]
   [app-clojure.data-store :refer [alimentos-atom]] ; <--- IMPORTE O ÁTOMO COMPARTILHADO AQUI
   [ring.util.response :refer [redirect response]])) ; Adicione 'redirect' e 'response' se não estiverem

;; Remova a definição local do átomo. Ele agora é importado de app-clojure.data-store.
;; (def alimentos-atom (atom []))

;; Um formatador de data/hora ISO 8601. É um formato universalmente aceito e fácil de ordenar.
(def iso-formatter (f/formatter :date-time-no-ms))

;; Função para registrar um alimento
(defn registrar-alimento [req]
  (let [alimento-pt (get-in req [:params :alimento])
        alimento-en (traduzir-pt-en alimento-pt)
        resultado (first (buscar-alimento alimento-en))
        data-str (get-in req [:params :data]) ; <-- pega a string do input date (yyyy-MM-dd)

        ;; Garante que data-parseada seja um objeto de data ou nil, não erro
        data-parseada (if (and data-str (not (empty? data-str)))
                        (try
                          (f/parse (f/formatter "yyyy-MM-dd") data-str)
                          (catch Exception _ (t/now))) ; Se houver erro no parse, usa a data atual
                        (t/now)) ; Se data-str for vazia ou nula, usa a data atual

        data-formatada (f/unparse (f/formatter "dd-MM-yyyy") data-parseada) ; <-- converte para dd-MM-yyyy
        registro {:alimento (capitalizar alimento-pt)
                  :calorias (:nf_calories resultado)
                  :dataRegistro (f/unparse iso-formatter (t/now)) ; data do sistema
                  :dataAdicao data-formatada}] ; <-- data fornecida pelo usuário, formatada

    (swap! alimentos-atom conj registro) ; <--- ATUALIZA O ÁTOMO COMPARTILHADO

    ;; Redireciona para a página principal para recarregar a tabela com os novos dados
    (redirect "/"))) ; <--- Use `redirect` para que o navegador recarregue a página

;; Função para listar alimentos salvos (lê do átomo compartilhado)
(defn listar-alimentos [_]
  ;; Esta função é a API que retorna JSON, lendo do átomo global
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body (str (cheshire.core/generate-string @alimentos-atom))}) ; <--- LÊ DO ÁTOMO COMPARTILHADO E RETORNA JSON