(ns front.user-section)


(defn user-section []
  [:div#user {:class "flex h-screen w-screen items-center justify-center mt-12 3xl:mt-0"}
   [:div {:class "flex h-screen w-screen items-center justify-center mt-12 3xl:mt-0"}
    [:div {:class "w-85/100 rounded-2xl py-6 px-12 shadow-2xl bg-white md:w-1/2 lg:w-1/3"}
     [:h1 {:class "text-center font-semibold text-slate-700 text-2xl"} "Informações Pessoais"]
     [:div {:class "my-5 flex flex-col gap-4"}

      ;; Nome
      [:div
       [:p {:class "p-2 text-slate-600 font-semibold"} "Nome*"]
       [:div {:class "flex items-center"}
        [:div {:class "h-10 w-11 rounded-l-xl border-y border-l border-slate-300 p-2 flex items-center justify-center"}
         [:i {:class "fa-solid fa-user text-slate-700"}]]
        [:input {:class "h-10 w-full rounded-r-xl border border-slate-300 p-2 text-slate-600 focus:outline-indigo-600"
                 :placeholder "Nome"}]]]

      ;; Gênero
      [:div
       [:p {:class "p-2 text-slate-600 font-semibold"} "Gênero*"]
       [:div {:class "flex items-center"}
        [:div {:class "h-10 w-11 rounded-l-xl border-y border-l border-slate-300 p-2 flex items-center justify-center"}
         [:i {:class "fa-solid fa-venus-mars text-slate-700"}]]
        [:div {:class "relative w-full z-0"}
         [:select {:class "h-10 w-full rounded-r-xl border border-slate-300 p-2 text-slate-600 focus:outline-indigo-600 appearance-none pr-8"}
          [:option {:value "Female"} "Mulher"]
          [:option {:value "Male"} "Homem"]]
         ;; Ícone do chevron
         [:div {:class "pointer-events-none absolute inset-y-0 right-1 flex items-center px-2 text-slate-700"}
          [:i {:class "fa-solid fa-chevron-down text-xs"}]]]]]

      ;; Idade
      [:div
       [:p {:class "p-2 text-slate-600 font-semibold"} "Idade*"]
       [:div {:class "flex items-center"}
        [:div {:class "h-10 w-11 rounded-l-xl border-y border-l border-slate-300 p-2 flex items-center justify-center"}
         [:i {:class "fa-solid fa-calendar-days text-slate-700"}]]
        [:input {:type "number"
                 :class "h-10 w-full rounded-r-xl border border-slate-300 p-2 text-slate-600 focus:outline-indigo-600"
                 :placeholder "Idade" :min "0" :max "120" :required true}]]]

      ;; Altura
      [:div
       [:p {:class "p-2 text-slate-600 font-semibold"} "Altura*"]
       [:div {:class "flex items-center"}
        [:div {:class "h-10 w-11 rounded-l-xl border-y border-l border-slate-300 p-2 flex items-center justify-center"}
         [:i {:class "fa-solid fa-ruler text-slate-700"}]]
        [:input {:type "number"
                 :class "h-10 w-full rounded-r-xl border border-slate-300 p-2 text-slate-600 focus:outline-indigo-600"
                 :placeholder "Altura(cm)" :min "0" :max "300" :required true}]]]

      ;; Peso
      [:div
       [:p {:class "p-2 text-slate-600 font-semibold"} "Peso*"]
       [:div {:class "flex items-center"}
        [:div {:class "h-10 w-11 rounded-l-xl border-y border-l border-slate-300 p-2 flex items-center justify-center"}
         [:i {:class "fa-solid fa-weight-scale text-slate-700"}]]
        [:input {:type "number"
                 :class "h-10 w-full rounded-r-xl border border-slate-300 p-2 text-slate-600 focus:outline-indigo-600"
                 :placeholder "Peso(kg)" :min "0" :required true}]]]

      [:button {:type "submit"
                :class "transition-all mt-3 h-10 cursor-pointer rounded-xl bg-indigo-500 p-2 font-semibold text-white duration-700 hover:bg-indigo-600"}
       "Enviar"]]]]])