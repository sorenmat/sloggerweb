$ ->
  $.get "/serverJson/Srens-MacBook-Pro.local", (data) ->
    $.each data, (index, item) ->
      $("#bars").append "<li>Bar " + item + "</li>"