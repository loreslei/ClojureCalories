(ns app-clojure.nutrition
  (:require [clj-http.client :as http]
            [cheshire.core :as json])
  (:gen-class))
  
(def app-id "d899c030")
(def app-key "fb001b6d8f9328292cc3d96c4c3679ce")

(defn buscar-alimento [texto]
  (let [url "https://trackapi.nutritionix.com/v2/natural/nutrients"
        headers {"x-app-id" app-id
                 "x-app-key" app-key
                 "Content-Type" "application/json"}
        body (json/generate-string {:query texto})
        resposta (http/post url {:headers headers :body body :as :json})]
    (get-in resposta [:body :foods])))

(defn buscar-exercicio [texto peso altura idade genero]
  (let [url "https://trackapi.nutritionix.com/v2/natural/exercise"
        headers {"x-app-id" app-id
                 "x-app-key" app-key
                 "Content-Type" "application/json"}
        body (json/generate-string
              {:query texto
               :gender genero
               :weight_kg peso
               :height_cm altura
               :age idade})
        resposta (http/post url {:headers headers :body body :as :json})]
    (get-in resposta [:body :exercises])))

