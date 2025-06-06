(ns app-clojure.food-exercises
  (:require
   [app-clojure.tradutor :refer [capitalizar traduzir-pt-en]]
   [app-clojure.nutrition :refer [buscar-exercicio]]
   [clj-time.core :as t]      ; <-- Importe clj-time.core
   [clj-time.format :as f]))  ; <-- Importe clj-time.format

;; Um formatador de data/hora ISO 8601, ideal para ordenação.
(def iso-formatter (f/formatter :date-time-no-ms))

;; Atom para armazenar exercicios e calorias
(def exercicios-atom (atom []))

;; Função para registrar um exercício
(defn registrar-exercicio [req]
  (let [exercicio-pt (get-in req [:params :exercicio])
        exercicio-en (traduzir-pt-en exercicio-pt)
        resultado (first (buscar-exercicio exercicio-en))
        data-str (get-in req [:params :data]) ; <-- pega a string do input date (yyyy-MM-dd)
        data-parseada (f/parse (f/formatter "yyyy-MM-dd") data-str) ; <-- parse ISO
        data-formatada (f/unparse (f/formatter "dd-MM-yyyy") data-parseada) ; <-- converte para dd-MM-yyyy
        registro {:exercicio (capitalizar exercicio-pt)
                  :calorias (:nf_calories resultado)
                  :dataRegistro (f/unparse iso-formatter (t/now)) ; data de envio 
                  :dataAdicao data-formatada}] ; data informada pelo usuário
    (swap! exercicios-atom conj registro)
    ;; Redirect para a página principal
    {:status 302
     :headers {"Location" "/"}
     :body ""}))

;; Função para listar exercícios salvos
(defn listar-exercicios [_]
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body @exercicios-atom})