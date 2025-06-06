(ns app-clojure.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.defaults :refer [api-defaults wrap-defaults]]
            [ring.middleware.content-type :refer [wrap-content-type]]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.util.response :refer [response status]]
            [front.front-end :refer [home-page]]
            [app-clojure.food-calories :refer [listar-alimentos  registrar-alimento]]
            [app-clojure.food-exercises :refer [listar-exercicios registrar-exercicio]]
            [app-clojure.user :refer [listar-usuario registrar-ususario]]))

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

;; Definição de rotas
(defroutes app-routes
  (POST "/registrar/alimento" [] registrar-alimento)
  (POST "/registrar/exercicio" [] registrar-exercicio)
  (POST "/registrar/usuario" [] registrar-ususario)
  (GET "/listar/alimentos" [] listar-alimentos)
  (GET "/listar/exercicios" [] listar-exercicios)
  (GET "/listar/usuario" [] listar-usuario)
  (GET "/" [] (home-page))
  (route/not-found {:status 404 :body "Não encontrado"}))


;; App
(def app
  (-> app-routes
      (wrap-resource "public")
      wrap-content-type
      wrap-params
      (wrap-json-body {:keywords? true})
      wrap-json-response
      (wrap-defaults api-defaults)))         


