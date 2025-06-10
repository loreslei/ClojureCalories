(ns app-clojure.user
  (:require
   [app-clojure.data-store :refer [alimentos-atom exercicios-atom]]
   [ring.util.response :refer [redirect]]))


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

    (reset! usuario-atom registro)


    (reset! alimentos-atom nil)
    (reset! exercicios-atom nil)

    (redirect "/")))


(defn listar-usuario [_]
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body (str (cheshire.core/generate-string @usuario-atom))})