(ns front.results-section
  (:require [clj-http.client :as http]
            [cheshire.core :as json]))

(defn limitar-ultimos [coll]
  (take-last 6 coll))

(defn results-section []
  (let [; Buscar TODOS os alimentos (agora com :dataRegistro)
        todos-alimentos (-> (http/get "http://localhost:3000/listar/alimentos" {:as :json})
                            :body)
        ; Buscar TODOS os exercícios (agora com :dataRegistro)
        todos-exercicios (-> (http/get "http://localhost:3000/listar/exercicios" {:as :json})
                             :body)

        ; --- Parte crucial: Combinar e Ordenar por dataRegistro ---
        ; 1. Concatenar todas as operações em uma única coleção
        todas-operacoes-raw (concat todos-alimentos todos-exercicios)

        ; 2. Ordenar essa coleção combinada pelo campo :dataRegistro
        ;    Assumimos que :dataRegistro é uma string em formato ISO 8601 (como "2025-06-05T19:28:03Z")
        ;    Strings neste formato são ordenáveis lexicograficamente, o que funciona para datas.
        todas-operacoes-ordenadas (sort-by :dataRegistro todas-operacoes-raw)

        ; Agora sim, limitar às ÚLTIMAS 5 operações da lista já ordenada cronologicamente
        ultimas-5-operacoes (limitar-ultimos todas-operacoes-ordenadas)
        ; -----------------------------------------------------

        ; Calcular o total com base nas operações limitadas
        ; Usamos `filter` para somar calorias de alimentos (positivas) e subtrair de exercícios (negativas)
        total (- (reduce + 0 (map :calorias (filter :alimento ultimas-5-operacoes)))
                 (reduce + 0 (map :calorias (filter :exercicio ultimas-5-operacoes))))

        ; Mapear para os itens da lista combinada, distinguindo entre alimento e exercício para a exibição
        itens (map (fn [item]
                     (if (:alimento item)
                       ; Se o item tiver a chave :alimento, é um alimento
                       [:li {:class "font-medium text-emerald-600"}
                        (str "+ " (int (:calorias item)) " calorias de " (:alimento item))]
                       ; Caso contrário, é um exercício (já que filtramos os tipos de entrada)
                       [:li {:class "font-medium text-rose-600"}
                        (str "- " (int (:calorias item)) " calorias de " (:exercicio item))]))
                   ultimas-5-operacoes)
        total-formatado (format "%d" (int total))]
    [:div {:id "results" :class "flex h-screen w-screen items-center justify-center"}
     [:div {:class "w-85/100 rounded-2xl py-6 px-12 shadow-2xl bg-white md:w-1/2 lg:w-1/3"}

      ;; Título
      [:h1 {:class "text-center font-semibold text-slate-700 text-2xl"}
       "6 Últimas Operações"]

      ;; Lista de resultados
      [:ul {:id "caloriesList" :class "text-center mt-3 p-3 flex flex-col gap-3"}
       (doall itens)]

      ;; ;; Linha divisória
      ;; [:hr]

      ;; ;; Total
      ;; [:h2 {:class "text-center p-3 text-slate-700"}
      ;;  [:span {:class "font-bold text-lg text-indigo-700"} "Total: "]
      ;;  [:span {:class "font-semibold"} total-formatado] " calorias"]
      ]]))