(ns app-clojure.user
  (:require
   [app-clojure.food-calories :refer [alimentos-atom]]
   [app-clojure.food-exercises :refer [exercicios-atom]]))

;; Atom para armazenar exercicios e calorias
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
                  :peso peso
                  }
        ]
    (reset! usuario-atom registro) 
    (reset! alimentos-atom nil)
    (reset! exercicios-atom nil)
    
    {:status 302
     :headers {"Location" "/"}
     :body ""}))

;; Rota GET para listar exercicios salvos
(defn listar-usuario [_]
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body @usuario-atom})


;;    {:status 200
;;    :headers {"Content-Type" "text/html; charset=utf-8"}
;;    :body (str "<h2>Usuario registrado: " nome "</h2>"
;;               "<a href='/'>Voltar</a>")}))

;; ;; Rota GET para listar exercicios salvos
;; (defn listar-usuario [_]
;;   {:status 200
;;    :body @usuario-atom})





