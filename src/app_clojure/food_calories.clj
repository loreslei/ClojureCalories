(ns app-clojure.food-calories
  (:require
   [app-clojure.tradutor :refer [capitalizar traduzir-pt-en]]
   [app-clojure.nutrition :refer [buscar-alimento]]
   [clj-time.core :as t]      ; <-- Importe clj-time.core
   [clj-time.format :as f]))  ; <-- Importe clj-time.format para formatar a data

;; Um formatador de data/hora ISO 8601. É um formato universalmente aceito e fácil de ordenar.
(def iso-formatter (f/formatter :date-time-no-ms))

;; Atom para armazenar alimentos e calorias
(def alimentos-atom (atom []))

;; Função para registrar um alimento
(defn registrar-alimento [req]
  (let [alimento-pt (get-in req [:params :alimento])
        alimento-en (traduzir-pt-en alimento-pt)
        resultado (first (buscar-alimento alimento-en))
        data-str (get-in req [:params :data]) ; <-- pega a string do input date (yyyy-MM-dd)
        data-parseada (f/parse (f/formatter "yyyy-MM-dd") data-str) ; <-- parse ISO
        data-formatada (f/unparse (f/formatter "dd-MM-yyyy") data-parseada) ; <-- converte para dd-MM-yyyy
        registro {:alimento (capitalizar alimento-pt)
                  :calorias (:nf_calories resultado)
                  :dataRegistro (f/unparse iso-formatter (t/now)) ; data do sistema
                  :dataAdicao data-formatada}] ; <-- data fornecida pelo usuário, formatada

    (swap! alimentos-atom conj registro)
    {:status 302
     :headers {"Location" "/"}
     :body ""}))



;; Função para listar alimentos salvos (pode ser JSON ou HTML dependendo do seu front)
(defn listar-alimentos [_]
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body @alimentos-atom})