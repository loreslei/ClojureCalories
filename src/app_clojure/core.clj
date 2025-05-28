(ns app-clojure.core
  (:require
   [clojure.string :as str]
   [app-clojure.nutrition :refer [buscar-alimento buscar-exercicio]]
   [app-clojure.tradutor :refer [traduzir-en-pt traduzir-pt-en]]
   
   [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
   [compojure.core :refer [defroutes GET]] 
   [compojure.route :as route])
  (:gen-class))

(defroutes app-routes
  (GET "/" [] "Olá, mundo!")
  (route/not-found "Página não encontrada"))

(def app
  (wrap-defaults app-routes site-defaults))

(defn imprimir-alimentos [alimentos]
  (dorun
   (map (fn [item]
          (println (traduzir-en-pt(:food_name item)) ":" (:nf_calories item) "calorias"))
        alimentos)))

(defn imprimir-exercicios [exercicios]
  (dorun
   (map (fn [item]
          (println (traduzir-en-pt (:name item))
                   (:nf_calories item) "calorias queimadas"
                   "em" (:duration_min item) "minutos"))
        exercicios)))



(defn menu []
  (print "\nO que deseja calcular?\n[A] Alimento\n[E] Exercicio\n[S] Sair\nDigite A, E ou S: ")
  (flush)
  (let [opcao (str/upper-case (read-line))]
    (cond
      (= opcao "A")
      (do
        (print "Digite os alimentos consumidos: ")
        (flush)
        (let [entrada (read-line) 
              resultado (buscar-alimento (traduzir-pt-en (str entrada)))
              ]
          ;; (println entrada)
          (println "\nResultado dos alimentos:")
          (imprimir-alimentos resultado)
        (recur))) ; recursão de cauda

      (= opcao "E")
      (do
        (print "Descreva o exercicio feito: ")
        (flush)
        (let [entrada (read-line)
              resultado (buscar-exercicio (traduzir-pt-en entrada))]
          (println "\nResultado dos exercicios:")
          (imprimir-exercicios resultado))
        (recur)) ; recursão de cauda

      (= opcao "S")
      (println "Encerrando o programa. Ate mais!")

      :else
      (do
        (println "Opcao invalida. Use A, E ou S.")
        (recur))))) ; recursão de cauda

(defn -main [& _] 
  
  (menu))