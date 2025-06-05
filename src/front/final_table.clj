(ns front.final-table)

(defn final-table []

  [:div {:class "w-85/100 rounded-lg bg-white flex flex-col items-center mt-20 mb-20 shadow-2xl md:w-1/2 lg:w-1/3 mx-auto"}
   [:div {:class "relative flex flex-col w-full h-full overflow-auto text-gray-700 bg-white rounded-t-lg"}
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
      [:tr {:class "hover:bg-slate-50 border-b border-slate-200"}
       [:td {:class "p-4 py-3 whitespace-nowrap"}
        [:span {:class "font-semibold uppercase bg-emerald-100 py-1 px-2 rounded text-sm text-emerald-800"} "Ganho"]]
       [:td {:class "p-4 py-3 whitespace-nowrap"}
        [:p {:class "text-sm text-slate-700"} "Hambúrguer"]]
       [:td {:class "p-4 py-3 whitespace-nowrap"}
        [:p {:class "text-sm font-semibold text-slate-700"} "200"]]
       [:td {:class "p-4 py-3 whitespace-nowrap"}
        [:p {:class "text-sm text-slate-700"} "04/06/2025"]]]
      [:tr {:class "hover:bg-slate-50 border-b border-slate-200"}
       [:td {:class "p-4 py-3 whitespace-nowrap"}
        [:span {:class "font-semibold uppercase bg-red-100 py-1 px-2 rounded text-sm text-red-800"} "Perda"]]
       [:td {:class "p-4 py-3 whitespace-nowrap"}
        [:p {:class "text-sm text-slate-700"} "Corrida"]]
       [:td {:class "p-4 py-3 whitespace-nowrap"}
        [:p {:class "text-sm font-semibold text-slate-700"} "150"]]
       [:td {:class "p-4 py-3 whitespace-nowrap"}
        [:p {:class "text-sm text-slate-700"} "04/06/2025"]]]]]
    [:div {:class "flex flex-col gap-3 md:flex-row justify-between items-center px-4 py-3"}
     [:div {:class "text-sm text-slate-500"}
      "Resultados " [:b "1-5"] " de " [:span {:id "totalPages"}] " página(s)"]
     [:div {:class "flex space-x-1" :id "pagination"}
      [:button {:id "prevBtn" :class "px-3 py-1 min-w-9 min-h-9 text-sm font-normal text-slate-500 bg-white border border-slate-200 rounded hover:bg-slate-50 hover:border-slate-400 transition duration-200 ease"} "Anterior"]
      [:button {:id "nextBtn" :class "px-3 py-1 min-w-9 min-h-9 text-sm font-normal text-slate-500 bg-white border border-slate-200 rounded hover:bg-slate-50 hover:border-slate-400 transition duration-200 ease"} "Próximo"]]]]]

  )