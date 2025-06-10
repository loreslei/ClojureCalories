(ns app-clojure.food-exercises
  (:require
   [app-clojure.tradutor :refer [capitalizar traduzir-pt-en]]
   [app-clojure.nutrition :refer [buscar-exercicio]]
   [clj-time.core :as t]
   [clj-time.format :as f]
   [app-clojure.data-store :refer [exercicios-atom]]
   [ring.util.response :refer [redirect]]))

; Um formatador de data/hora
(def iso-formatter (f/formatter :date-time-no-ms))

;; Função para registrar um exercício
(defn registrar-exercicio [req]
  (let [exercicio-pt (get-in req [:params :exercicio])
        exercicio-en (traduzir-pt-en exercicio-pt)
        resultado (first (buscar-exercicio exercicio-en))
        data-str (get-in req [:params :data]) ; <-- pega a string do input date (yyyy-MM-dd)

        data-parseada (if (and data-str (not (empty? data-str)))
                        (try
                          (f/parse (f/formatter "yyyy-MM-dd") data-str)
                          (catch Exception _ (t/now)))
                        (t/now))

        data-formatada (f/unparse (f/formatter "dd-MM-yyyy") data-parseada) ; <-- converte para dd-MM-yyyy
        registro {:exercicio (capitalizar exercicio-pt)
                  :calorias (:nf_calories resultado)
                  :dataRegistro (f/unparse iso-formatter (t/now)) ; data de envio 
                  :dataAdicao data-formatada}] ; data informada pelo usuário

    (swap! exercicios-atom conj registro)


    (redirect "/")))

;; Função para listar exercícios salvos
(defn listar-exercicios [_]
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body (str (cheshire.core/generate-string @exercicios-atom))})