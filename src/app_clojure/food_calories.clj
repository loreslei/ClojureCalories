(ns app-clojure.food-calories 
  (:require
   [cheshire.core :refer [generate-string]]))

(defn como-json [conteudo & [status]]
  {:status (or status 200)
   :headers {"Content-Type" "application/json; charset=utf-8"}
   :body (generate-string conteudo)})

;; Atom para armazenar alimentos e calorias
(def alimentos-atom (atom []))

;; Atom para armazenar exercicios e calorias
(def exercicios-atom (atom []))

;Rota POST para receber alimento e quantidade e salvar no atom
(defn adicionar-alimento [req]
  (let [
        ;;body (:body req)
        alimento (:alimento req)
        calorias (:calorias req)
        registro {:alimento alimento
                  :calorias calorias}] 
    
    (swap! alimentos-atom conj registro)
    (como-json {:success true
                :mensagem "Alimento registrado com sucesso!"
                :registro registro})))

;Rota POST para receber exercicio e quantidade e salvar no atom
(defn adicionar-exercicio [req]
  (let [
        ;;body (:body req)
        exercicio (:exercicio req)
        calorias (:calorias req)
        registro {:exercicio exercicio
                  :calorias calorias}] 
    (swap! exercicios-atom conj registro)
    (como-json {:success true
                :mensagem "Exercicio registrado com sucesso!"
                :registro registro})))


;; Rota GET para listar alimentos salvos
(defn listar-alimentos [_]
  {:status 200
   :body @alimentos-atom})

;; Rota GET para listar alimentos salvos
(defn listar-exercicios [_]
  {:status 200
   :body @exercicios-atom})





