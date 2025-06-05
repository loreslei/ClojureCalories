(ns app-clojure.food-calories 
  (:require
   [cheshire.core :refer [generate-string]]
   [app-clojure.tradutor :refer [traduzir-en-pt traduzir-pt-en]]
   [app-clojure.nutrition :refer [buscar-alimento buscar-exercicio]]
  ))

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

(defn imprimir-alimentos [items]
  ;; Pega o primeiro mapa de alimento do vetor
  (let [item (first items)
        registro {:alimento (traduzir-en-pt (:food_name item))
                  :calorias (:nf_calories item)}]
    (adicionar-alimento registro)
    (println "Alimento enviado ao servidor:" registro)))

(defn registrar-alimento [req]
  ;;(println "REQ PARAMS:" (:params req))
  (let [alimento-pt (:alimento (:params req))
        alimento-en (traduzir-pt-en alimento-pt)
        resultado (buscar-alimento alimento-en)
        ]
    (imprimir-alimentos resultado)
    ;;(println "REQ PARAMS:" alimento-pt)
    {:status 200
     :headers {"Content-Type" "text/html; charset=utf-8"}
     :body (str "<h2>Alimento registrado: " alimento-pt "</h2>"
                "<a href='/'>Voltar</a>")}))

(defn imprimir-exercicios [exercicios]
  (let [exercicio (first exercicios)
        registro {:exercicio (traduzir-en-pt (:name exercicio))
                  :calorias (:nf_calories exercicio)}]
    (adicionar-exercicio registro)
    (println "Exercicio enviado ao servidor:" registro)))


(defn registrar-exercicio [req]
  ;(println "REQ PARAMS:" (:params req))
  (let [exercicio-pt (:exercicio (:params req))
        exercicio-en (traduzir-pt-en exercicio-pt)
        resultado (buscar-exercicio exercicio-en)
        ]
    (imprimir-exercicios resultado)
    ;(println "exercicio pt" exercicio-pt)
    {:status 200
     :headers {"Content-Type" "text/html; charset=utf-8"}
     :body (str "<h2>Alimento registrado: " exercicio-pt "</h2>"
                "<a href='/'>Voltar</a>")}))

;; Rota GET para listar alimentos salvos
(defn listar-alimentos [_]
  {:status 200
   :body @alimentos-atom})

;; Rota GET para listar alimentos salvos
(defn listar-exercicios [_]
  {:status 200
   :body @exercicios-atom})





