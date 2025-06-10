(ns front.results-section
  (:require [clj-http.client :as http]))

(defn limitar-ultimos [coll]
  (take-last 6 coll))

(defn results-section []
  (let [todos-alimentos (-> (http/get "http://localhost:3000/listar/alimentos" {:as :json})
                            :body)

        todos-exercicios (-> (http/get "http://localhost:3000/listar/exercicios" {:as :json})
                             :body)


        ;Concatenar todas as operações em uma única coleção
        todas-operacoes-raw (concat todos-alimentos todos-exercicios)

        ; Ordenar essa coleção combinada pelo campo :dataRegistro 
        todas-operacoes-ordenadas (sort-by :dataRegistro todas-operacoes-raw)

        ultimas-operacoes (limitar-ultimos todas-operacoes-ordenadas)
        ; -----------------------------------------------------

        ; Calcular o total com base nas operações limitadas
        ; somar calorias de alimentos (positivas) e subtrair de exercícios (negativas)
        total (- (reduce + 0 (map :calorias (filter :alimento ultimas-operacoes)))
                 (reduce + 0 (map :calorias (filter :exercicio ultimas-operacoes))))

        ; distinguindo entre alimento e exercício para a exibição
        itens (map (fn [item]
                     (if (:alimento item)

                       [:li {:class "font-medium text-emerald-600"}
                        (str "+ " (int (:calorias item)) " calorias de " (:alimento item))]
                       [:li {:class "font-medium text-rose-600"}
                        (str "- " (int (:calorias item)) " calorias de " (:exercicio item))]))
                   ultimas-operacoes)]
    [:div {:id "results" :class "flex h-screen w-screen items-center justify-center"}
     [:div {:class "w-85/100 rounded-2xl py-6 px-12 shadow-2xl bg-white md:w-1/2 lg:w-1/3"}

      ;; Título
      [:h1 {:class "text-center font-semibold text-slate-700 text-2xl"}
       "6 Últimas Operações"]

      ;; Lista de resultados
      [:ul {:id "caloriesList" :class "text-center mt-3 p-3 flex flex-col gap-3"}
       (doall itens)]]]))