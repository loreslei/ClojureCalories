(ns app-clojure.core
  (:require
   [clojure.string :as str]
   [app-clojure.nutrition :refer [buscar-alimento buscar-exercicio]])
  (:gen-class))





(defn imprimir-alimentos [alimentos]
  (dorun
   (map (fn [item]
          (println (:food_name item) ":" (:nf_calories item) "calorias"))
        alimentos)))

(defn imprimir-exercicios [exercicios]
  (dorun
   (map (fn [item]
          (println (:name item)
                   (:nf_calories item) "calorias queimadas"
                   "em" (:duration_min item) "minutos"))
        exercicios)))


(defn menu [idade altura peso genero]
  (print "\nO que deseja calcular?\n[A] Alimento\n[E] Exercicio\n[S] Sair\nDigite A, E ou S: ")
  (flush)
  (let [opcao (str/upper-case (read-line))]
    (cond
      (= opcao "A")
      (do
        (print "Digite os alimentos consumidos: ")
        (flush)
        (let [entrada (read-line)
              resultado (buscar-alimento entrada)]
          (println "\nResultado dos alimentos:")
          (imprimir-alimentos resultado))
        (recur idade altura peso genero))

      (= opcao "E")
      (do
        (print "Descreva o exercicio feito: ")
        (flush)
        (let [entrada (read-line)
              resultado (buscar-exercicio entrada peso altura idade genero)]
          (println "\nResultado dos exercicios:")
          (imprimir-exercicios resultado))
        (recur idade altura peso genero)) ; recursão de cauda

      (= opcao "S")
      (println "Encerrando o programa. Ate mais!")

      :else
      (do
        (println "Opcao invalida. Use A, E ou S.")
        (recur idade altura peso genero))))) ; recursão de cauda

(defn -main [& _]
  (def diario
    (atom
     {:data "2025-05-25"
      :alimentos []
      :atividades []}))

  (let [_ (println "Digite seu nome: ")
        nome (read-line)

        _ (println "Digite seu genero: ")
        genero (read-line)

        _ (println "Digite sua idade: ")
        idade (read-line)

        _ (println "Digite sua altura: ")
        altura (read-line)

        _ (println "Digite seu peso: ")
        peso (read-line)]
    
    (println "\nDados capturados:")
    (println "Nome:" nome)
    (println "Genero:" genero)
    (println "Idade:" idade)
    (println "Altura:" altura)
    (println "Peso:" peso)
    (menu idade altura peso genero) 
    )
)