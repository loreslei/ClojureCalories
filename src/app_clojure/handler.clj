(ns app-clojure.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.defaults :refer [api-defaults wrap-defaults]]
            [ring.middleware.content-type :refer [wrap-content-type]]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.util.response :refer [response status redirect]]
            [front.front-end :refer [home-page]]
            [app-clojure.food-calories :refer [listar-alimentos registrar-alimento]]
            [app-clojure.food-exercises :refer [listar-exercicios registrar-exercicio]]
            [app-clojure.user :refer [listar-usuario registrar-ususario]]
            [app-clojure.filter :refer [filtrar-data]]
            [app-clojure.data-store :refer [alimentos-atom exercicios-atom carregar-dados-iniciais!]]))

;; Carrega os dados iniciais quando o handler é carregado.
;; Já é chamado no `app-clojure.data-store`, então não precisa ser aqui novamente.

;; Controller de perda (mantido como está)
(defn calcular-perda [req]
  (let [{:keys [peso calorias]} (:body req)]
    (if (and peso calorias)
      (response {:resultado (- calorias (* peso 10))})
      (status (response {:status "error"
                         :message "Parâmetros 'peso' e 'calorias' são obrigatórios."}) 400))))

;; Controller de ganho (mantido como está)
(defn calcular-ganho [req]
  (let [{:keys [peso calorias]} (:body req)]
    (if (and peso calorias)
      (response {:resultado (+ calorias (* peso 10))})
      (status (response {:status "error"
                         :message "Parâmetros 'peso' e 'calorias' são obrigatórios."}) 400))))

;; Definição de rotas
(defroutes app-routes
  ;; Rotas de registro - CORREÇÃO AQUI: adicione `req` como argumento para as funções
  (POST "/registrar/alimento" req (registrar-alimento req)) ; <--- CORRIGIDO
  (POST "/registrar/exercicio" req (registrar-exercicio req)) ; <--- CORRIGIDO
  (POST "/registrar/usuario" req (registrar-ususario req)) ; Provavelmente também precisa de `req`

  ;; Rotas de listagem: Continuam retornando JSON, lendo dos átomos globais
  (GET "/listar/alimentos" [] (listar-alimentos nil))
  (GET "/listar/exercicios" [] (listar-exercicios nil))
  (GET "/listar/usuario" [] listar-usuario)

  ;; Rota para a página inicial (sem filtro aplicado inicialmente)
  (GET "/" []
    (let [todas-operacoes (sort-by :dataAdicao (concat @alimentos-atom @exercicios-atom))]
      (home-page todas-operacoes)))

  ;; Rota para o filtro
  (GET "/filtrar" req
    (let [operacoes-filtradas (filtrar-data req)]
      (home-page operacoes-filtradas)))

  (route/not-found {:status 404 :body "Não encontrado"}))

;; App (middleware stack)
(def app
  (-> app-routes
      (wrap-resource "public")
      wrap-content-type
      wrap-params
      (wrap-json-body {:keywords? true})
      wrap-json-response
      (wrap-defaults api-defaults)))