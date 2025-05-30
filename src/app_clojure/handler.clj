(ns app-clojure.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.content-type :refer [wrap-content-type]]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            [ring.util.response :refer [response status]]
            [front.front-end :refer [home-page]]))

;; Controller de perda
(defn calcular-perda [req]
  (let [{:keys [peso calorias]} (:body req)]
    (if (and peso calorias)
      (response {:resultado (- calorias (* peso 10))})
      (status (response {:status "error"
                         :message "Parâmetros 'peso' e 'calorias' são obrigatórios."}) 400))))

;; Controller de ganho
(defn calcular-ganho [req]
  (let [{:keys [peso calorias]} (:body req)]
    (if (and peso calorias)
      (response {:resultado (+ calorias (* peso 10))})
      (status (response {:status "error"
                         :message "Parâmetros 'peso' e 'calorias' são obrigatórios."}) 400))))

;; Rotas
(defroutes app-routes
  (GET "/" [] (home-page))
  (POST "/perda" [] calcular-perda)
  (POST "/ganho" [] calcular-ganho)
  (route/not-found (response {:status "error" :message "Rota não encontrada"})))

;; App
(def app
  (-> app-routes
      (wrap-resource "public")      ;; serve static resources da pasta resources/public
      wrap-content-type             ;; adiciona content-type correto para os arquivos
      (wrap-json-body {:keywords? true}) ;; parse JSON com keywords para facilitar desestruturação
      wrap-json-response))
