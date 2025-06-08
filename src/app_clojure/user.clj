(ns app-clojure.user
  (:require
   [app-clojure.data-store :refer [alimentos-atom exercicios-atom]] ; <--- IMPORTE OS ÁTOMOS DO data-store AQUI
   [ring.util.response :refer [redirect response]])) ; Adicione 'redirect' e 'response' para usar na resposta HTTP

;; Atom para armazenar dados do usuário (este sim pode ser local, se for um único usuário)
(def usuario-atom (atom []))

(defn registrar-ususario [req]
  (let [nome (:nome (:params req))
        genero (:genero (:params req))
        idade (:idade (:params req))
        altura (:altura (:params req))
        peso (:peso (:params req))
        registro {:nome nome
                  :genero genero
                  :idade idade
                  :altura altura
                  :peso peso}]

    (reset! usuario-atom registro) ; Atualiza os dados do usuário

    
    (reset! alimentos-atom nil) 
    (reset! exercicios-atom nil)

    ;; Redireciona para a página principal após o registro do usuário
    (redirect "/"))) ; Use `redirect` para que o navegador recarregue a página

;; Função para listar dados do usuário
(defn listar-usuario [_]
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body (str (cheshire.core/generate-string @usuario-atom))})