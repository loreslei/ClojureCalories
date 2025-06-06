(ns front.final-table
  (:require [clj-http.client :as http]
            [cheshire.core :as json]))

(defn final-table []
  (let [; Busca dados reais da API
        alimentos (-> (http/get "http://localhost:3000/listar/alimentos" {:as :json}) :body)
        exercicios (-> (http/get "http://localhost:3000/listar/exercicios" {:as :json}) :body)

        
        todas-operacoes (sort-by :dataAdicao (concat alimentos exercicios))
        

        ; Monta linhas da tabela
        linhas (map (fn [item]
                      (let [tipo (if (:alimento item) "Ganho" "Perda")
                            nome (or (:alimento item) (:exercicio item))
                            calorias (int (:calorias item))
                            data (:dataAdicao item)
                            cor (case tipo
                                  "Ganho" "bg-emerald-100 text-emerald-800"
                                  "Perda" "bg-red-100 text-red-800")]
                        [:tr {:class "hover:bg-slate-50 border-b border-slate-200"}
                         [:td {:class "p-4 py-3 whitespace-nowrap"}
                          [:span {:class (str "font-semibold uppercase py-1 px-2 rounded text-sm " cor)} tipo]]
                         [:td {:class "p-4 py-3 whitespace-nowrap"}
                          [:p {:class "text-sm text-slate-700"} nome]]
                         [:td {:class "p-4 py-3 whitespace-nowrap"}
                          [:p {:class "text-sm font-semibold text-slate-700"} calorias]]
                         [:td {:class "p-4 py-3 whitespace-nowrap"}
                          [:p {:class "text-sm text-slate-700"} data]]]))
                    todas-operacoes)

        total (- (reduce + 0 (map :calorias (filter :alimento todas-operacoes)))
                 (reduce + 0 (map :calorias (filter :exercicio todas-operacoes))))
        total-formatado (int total)
        ]

    [:div {:class "w-85/100 rounded-lg bg-white flex flex-col items-center mt-20 mb-20 shadow-2xl md:w-1/2 lg:w-1/3 mx-auto"}
     [:div {:class "relative flex flex-col w-full h-full overflow-auto text-gray-700 bg-white rounded-lg"}
      [:table {:class "w-full text-left table-auto min-w-max appearance-none"}
       [:thead {:class "rounded-xl"}
        [:tr
         [:th {:class "p-4 border-b border-slate-200 bg-slate-50"}
          [:p {:class "text-sm leading-none text-slate-700"} "Tipo Calórico"]]
         [:th {:class "p-4 border-b border-slate-200 bg-slate-50"}
          [:p {:class "text-sm leading-none text-slate-700"} "Nome"]]
         [:th {:class "p-4 border-b border-slate-200 bg-slate-50"}
          [:p {:class "text-sm leading-none text-slate-700"} "Calorias"]]
         [:th {:class "p-4 border-b border-slate-200 bg-slate-50"}
          [:p {:class "text-sm leading-none text-slate-700"} "Data"]]]]
       [:tbody {:class "divide-y divide-gray-100"}
        (doall linhas)
        [:tr {:class "hover:bg-slate-50 border-b border-slate-200"}
         [:td {:class "p-4 py-3 whitespace-nowrap"}
          [:span {:class "font-semibold uppercase bg-slate-200 py-1 px-2 rounded text-sm text-slate-800"} "Total"]]
         [:td {:class "p-4 py-3 whitespace-nowrap" :colspan 3}
          [:p {:class "text-sm text-center font-semibold text-slate-700"} (str total-formatado " calorias")]]]]]

      ;; Paginação fictícia
      [:div {:class "flex flex-col gap-3 md:flex-row justify-between items-center px-4 py-3"}
       [:div {:class "text-sm text-slate-500"}
        "Resultados " [:b "1-6"] " de " [:span {:id "totalPages"} "1"] " página(s)"]
       [:div {:class "flex space-x-1" :id "pagination"}
        [:button {:id "prevBtn" :class "px-3 py-1 min-w-9 min-h-9 text-sm font-normal text-slate-500 bg-white border border-slate-200 rounded hover:bg-slate-50 hover:border-slate-400 transition duration-200 ease"} "Anterior"]
        [:button {:id "nextBtn" :class "px-3 py-1 min-w-9 min-h-9 text-sm font-normal text-slate-500 bg-white border border-slate-200 rounded hover:bg-slate-50 hover:border-slate-400 transition duration-200 ease"} "Próximo"]]]]]))
