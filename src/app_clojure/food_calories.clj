(ns app-clojure.food-calories
  (:require
   [app-clojure.tradutor :refer [capitalizar traduzir-pt-en]]
   [app-clojure.nutrition :refer [buscar-alimento]]
   [clj-time.core :as t]
   [clj-time.format :as f]
   [app-clojure.data-store :refer [alimentos-atom]]
   [ring.util.response :refer [redirect]]))


;; Um formatador de data/hora
(def iso-formatter (f/formatter :date-time-no-ms))

;; Função para registrar um alimento
(defn registrar-alimento [req]
  (let [alimento-pt (get-in req [:params :alimento])
        alimento-en (traduzir-pt-en alimento-pt)
        resultado (first (buscar-alimento alimento-en))
        data-str (get-in req [:params :data]) ; <-- pega a string do input date (yyyy-MM-dd)

        ;; Garante que data-parseada seja um objeto de data ou nil
        data-parseada (if (and data-str (not (empty? data-str)))
                        (try
                          (f/parse (f/formatter "yyyy-MM-dd") data-str)
                          (catch Exception _ (t/now)))
                        (t/now))

        data-formatada (f/unparse (f/formatter "dd-MM-yyyy") data-parseada) ; <-- converte para dd-MM-yyyy
        registro {:alimento (capitalizar alimento-pt)
                  :calorias (:nf_calories resultado)
                  :dataRegistro (f/unparse iso-formatter (t/now)) ; data do sistema
                  :dataAdicao data-formatada}] ; <-- data fornecida pelo usuário, formatada

    (swap! alimentos-atom conj registro)


    (redirect "/")))

;; Função para listar alimentos salvos
(defn listar-alimentos [_]
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body (str (cheshire.core/generate-string @alimentos-atom))}) ; RETORNA JSON