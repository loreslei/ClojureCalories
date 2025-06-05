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
            [app-clojure.food-calories :refer [adicionar-alimento listar-alimentos listar-exercicios registrar-alimento registrar-exercicio]] 
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
  (POST "/registrar/alimento" [] registrar-alimento)
  (POST "/registrar/exercicio" [] registrar-exercicio)
  (GET "/listar/alimentos" [] listar-alimentos)
  (GET "/listar/exercicios" [] listar-exercicios)
  (GET "/" [] (home-page))
  (route/not-found {:status 404 :body "Não encontrado"}))


;; App
(def app
  (-> app-routes
      (wrap-resource "public")               ; para arquivos estáticos
      wrap-content-type                      ; content-type correto
      wrap-params                            ; necessário para extrair params de formulários (OBRIGATÓRIO!)
      (wrap-json-body {:keywords? true})     ; só faz sentido para JSON (API)
      wrap-json-response                     ; transforma mapas em JSON (API)
      (wrap-defaults api-defaults)))         ; configurações padrão para API




;; ;; Aplicação com middlewares
;; (def app
;;   (-> app-routes
;;       (wrap-json-body {:keywords? true}) ; <- primeiro
;;       wrap-json-response
;;       (wrap-defaults api-defaults)))
