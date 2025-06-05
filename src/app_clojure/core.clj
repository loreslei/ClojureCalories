(ns app-clojure.core
  (:require
   [app-clojure.nutrition :refer [buscar-alimento buscar-exercicio]]
   [app-clojure.tradutor :refer [traduzir-en-pt traduzir-pt-en]]
   [clojure.string :as str]
   [compojure.core :refer :all]
   [ring.adapter.jetty :refer [run-jetty]]
   ;;[clj-http.client :as client]
   [app-clojure.food-calories :refer [adicionar-alimento adicionar-exercicio listar-alimentos imprimir-alimentos imprimir-exercicios]]
   [app-clojure.handler :refer [app app-routes]]
   )
  (:gen-class))




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
              resultado (buscar-alimento (traduzir-pt-en (str entrada)))]
          ;;(println entrada)
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
  (run-jetty app {:port 3000 :join? false})
  (menu))