(ns app-clojure.nutrition
  (:require [clj-http.client :as http]
            [cheshire.core :refer :all]
            [cheshire.core :as json]
            [clojure.string :as str])
  (:gen-class))

(def app-id (System/getenv "app_id"))
(def app-key (System/getenv "app_key"))

;; (def app-id "d899c030")
;; (def app-key "fb001b6d8f9328292cc3d96c4c3679ce")

;;(println "App ID:" app-id)
;;(println "App Key:" app-key)

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
        body (json/generate-string
              {:query texto
               :gender "female"
               :weight_kg 70
               :height_cm 158
               :age 28})
        resposta (http/post url {:headers headers :body body :as :json})]
    (get-in resposta [:body :exercises])))

;(print (buscar-alimento "Bread  "))
;(print (buscar-alimento "Bread  %"))
;(print (buscar-alimento "Bread  %~~"))