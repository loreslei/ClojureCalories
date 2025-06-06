(ns app-clojure.nutrition
  (:require [clj-http.client :as http] 
            [cheshire.core :as json]
            )
  (:gen-class))

(def app-id (System/getenv "app_id"))
(def app-key (System/getenv "app_key"))

;; (def app-id "d899c030")
;; (def app-key "fb001b6d8f9328292cc3d96c4c3679ce")

(defn buscar-alimento [texto]
  (let [url "https://trackapi.nutritionix.com/v2/natural/nutrients"
        headers {"x-app-id" app-id
                 "x-app-key" app-key
                 "Content-Type" "application/json; charset=UTF-8"}
        body (json/generate-string {:query texto})
        resposta (http/post url {:headers headers
                                 :body body
                                 :as :json
                                 :keywords? true})]
    (get-in resposta [:body :foods])))

(defn buscar-exercicio [texto]
  (let [url "https://trackapi.nutritionix.com/v2/natural/exercise"
        headers {"x-app-id" app-id
                 "x-app-key" app-key
                 "Content-Type" "application/json"}
        usuario-response (http/get "http://localhost:3000/listar/usuario" {:as :json})
        usuario (:body usuario-response)
        genero (:genero usuario)
        peso (:peso usuario)
        altura (:altura usuario)
        idade (:idade usuario)

        body (json/generate-string
              {:query texto
               :gender genero
               :weight_kg peso
               :height_cm altura
               :age idade})

        resposta (http/post url {:headers headers :body body :as :json :keywords? true})]
    (println genero peso altura idade)
    (get-in resposta [:body :exercises])))