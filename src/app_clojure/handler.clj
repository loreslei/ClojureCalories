(ns app-clojure.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.defaults :refer [api-defaults wrap-defaults]]
            [ring.middleware.content-type :refer [wrap-content-type]]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            [ring.util.response :refer [response status]]
            [front.front-end :refer [home-page]]
            [app-clojure.food-calories :refer [adicionar-alimento listar-alimentos listar-exercicios]]
            ))

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
  (POST "/adicionar/alimento" [] adicionar-alimento)
  (POST "/adicionar/exercicio" [] adicionar-alimento) 
  (GET "/listar/alimentos" [] listar-alimentos)
  (GET "/listar/exercicios" [] listar-exercicios)
  (GET "/" [] (home-page))
  (route/not-found {:status 404 :body "Não encontrado"}))


;; App
(def app
  (-> app-routes
      (wrap-resource "public")      ;; serve static resources da pasta resources/public
      wrap-content-type             ;; adiciona content-type correto para os arquivos
      (wrap-json-body {:keywords? true}) ;; parse JSON com keywords para facilitar desestruturação
      wrap-json-response
      (wrap-defaults api-defaults)
      ))



;; ;; Aplicação com middlewares
;; (def app
;;   (-> app-routes
;;       (wrap-json-body {:keywords? true}) ; <- primeiro
;;       wrap-json-response
;;       (wrap-defaults api-defaults)))
