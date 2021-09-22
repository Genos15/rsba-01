package com.rsba.notificationmicroservice.utils

import com.rsba.notificationmicroservice.domain.SingleInviteUserReturn

object EmailMessageHelper {
    fun html(uri: String? = "#", company: String? = null) = """
        <!doctype html>
        <html class="no-js" lang="">
        <head>
          <meta content="text/html; charset=UTF-8" http-equiv="Content-Type">
          <title>Подтвердите свой электронный адрес</title>
          <style>
            body { 
              overflow: hidden;
              padding: 20%;
            }

            .submit-button {
              min-height: 2.5rem;
              min-width: 300px;
              max-width: 400px;
              background-color: #0353e9;
              color: white;
              text-decoration: none;
              display: flex;
              flex-direction: column;
              justify-content: center;
              font-weight: bold;
              padding: .5rem;
            }

            .arrow-right {
              position: relative;
            }

            .arrow-right::before {
              position: absolute;
              top: 15px;
              right: 10px;
              background-image: url(data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZlcnNpb249IjEuMSIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiIHhtbG5zOnN2Z2pzPSJodHRwOi8vc3ZnanMuY29tL3N2Z2pzIiB3aWR0aD0iNTEyIiBoZWlnaHQ9IjUxMiIgeD0iMCIgeT0iMCIgdmlld0JveD0iMCAwIDUxMiA1MTIiIHN0eWxlPSJlbmFibGUtYmFja2dyb3VuZDpuZXcgMCAwIDUxMiA1MTIiIHhtbDpzcGFjZT0icHJlc2VydmUiPjxnPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgoJPGc+CgkJPHBhdGggZD0iTTUwNi4xMzQsMjQxLjg0M2MtMC4wMDYtMC4wMDYtMC4wMTEtMC4wMTMtMC4wMTgtMC4wMTlsLTEwNC41MDQtMTA0Yy03LjgyOS03Ljc5MS0yMC40OTItNy43NjItMjguMjg1LDAuMDY4ICAgIGMtNy43OTIsNy44MjktNy43NjIsMjAuNDkyLDAuMDY3LDI4LjI4NEw0NDMuNTU4LDIzNkgyMGMtMTEuMDQ2LDAtMjAsOC45NTQtMjAsMjBjMCwxMS4wNDYsOC45NTQsMjAsMjAsMjBoNDIzLjU1NyAgICBsLTcwLjE2Miw2OS44MjRjLTcuODI5LDcuNzkyLTcuODU5LDIwLjQ1NS0wLjA2NywyOC4yODRjNy43OTMsNy44MzEsMjAuNDU3LDcuODU4LDI4LjI4NSwwLjA2OGwxMDQuNTA0LTEwNCAgICBjMC4wMDYtMC4wMDYsMC4wMTEtMC4wMTMsMC4wMTgtMC4wMTlDNTEzLjk2OCwyNjIuMzM5LDUxMy45NDMsMjQ5LjYzNSw1MDYuMTM0LDI0MS44NDN6IiBmaWxsPSIjZmZmZmZmIiBkYXRhLW9yaWdpbmFsPSIjMDAwMDAwIiBzdHlsZT0iIj48L3BhdGg+Cgk8L2c+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPC9nPjwvc3ZnPg==);
              background-size: contain;
              display: inline-block;
              content: "";
              width: 1rem;
              height: 1rem;
            }
            
            .white-elt {
                color: white;
            }
          </style>
        </head>

        <body>
        <h1>Ты почти закончил!</h1>
        <p>Вы добавили новый адрес электронной почты в свой аккаунт.</p>
        <p>Компания $company.</p>
        <p>Нажмите кнопку "Подтвердить", и мы импортируем уже сделанные
          бронирования по этому адресу.</p>
        <br/>
        <br/>
        <br/>
        <a href="$uri">
            <span class="white-elt">Подтвердить</span>
        </a>
        </body>
        </html>
    """.trimIndent()

    fun inviteHtml(uri: String? = "#", company: String? = null) = """
        <!doctype html>
        <html class="no-js" lang="">
        <head>
          <meta content="text/html; charset=UTF-8" http-equiv="Content-Type">
          <title>Подтвердите свой электронный адрес</title>
          <style>
            body { 
              overflow: hidden;
              padding: 20%;
            }

            .submit-button {
              min-height: 2.5rem;
              min-width: 300px;
              max-width: 400px;
              background-color: #0353e9;
              color: white;
              text-decoration: none;
              display: flex;
              flex-direction: column;
              justify-content: center;
              font-weight: bold;
              padding: .5rem;
            }

            .arrow-right {
              position: relative;
            }

            .arrow-right::before {
              position: absolute;
              top: 15px;
              right: 10px;
              background-image: url(data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZlcnNpb249IjEuMSIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiIHhtbG5zOnN2Z2pzPSJodHRwOi8vc3ZnanMuY29tL3N2Z2pzIiB3aWR0aD0iNTEyIiBoZWlnaHQ9IjUxMiIgeD0iMCIgeT0iMCIgdmlld0JveD0iMCAwIDUxMiA1MTIiIHN0eWxlPSJlbmFibGUtYmFja2dyb3VuZDpuZXcgMCAwIDUxMiA1MTIiIHhtbDpzcGFjZT0icHJlc2VydmUiPjxnPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgoJPGc+CgkJPHBhdGggZD0iTTUwNi4xMzQsMjQxLjg0M2MtMC4wMDYtMC4wMDYtMC4wMTEtMC4wMTMtMC4wMTgtMC4wMTlsLTEwNC41MDQtMTA0Yy03LjgyOS03Ljc5MS0yMC40OTItNy43NjItMjguMjg1LDAuMDY4ICAgIGMtNy43OTIsNy44MjktNy43NjIsMjAuNDkyLDAuMDY3LDI4LjI4NEw0NDMuNTU4LDIzNkgyMGMtMTEuMDQ2LDAtMjAsOC45NTQtMjAsMjBjMCwxMS4wNDYsOC45NTQsMjAsMjAsMjBoNDIzLjU1NyAgICBsLTcwLjE2Miw2OS44MjRjLTcuODI5LDcuNzkyLTcuODU5LDIwLjQ1NS0wLjA2NywyOC4yODRjNy43OTMsNy44MzEsMjAuNDU3LDcuODU4LDI4LjI4NSwwLjA2OGwxMDQuNTA0LTEwNCAgICBjMC4wMDYtMC4wMDYsMC4wMTEtMC4wMTMsMC4wMTgtMC4wMTlDNTEzLjk2OCwyNjIuMzM5LDUxMy45NDMsMjQ5LjYzNSw1MDYuMTM0LDI0MS44NDN6IiBmaWxsPSIjZmZmZmZmIiBkYXRhLW9yaWdpbmFsPSIjMDAwMDAwIiBzdHlsZT0iIj48L3BhdGg+Cgk8L2c+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPC9nPjwvc3ZnPg==);
              background-size: contain;
              display: inline-block;
              content: "";
              width: 1rem;
              height: 1rem;
            }
            
            .white-elt {
                color: white;
            }
          </style>
        </head>

        <body>
        <h1>Ты почти закончил!</h1>
        <p>Вы добавили новый адрес электронной почты в свой аккаунт.</p>
        <p>Компания $company.</p>
        <p>Нажмите кнопку "Подтвердить", и мы импортируем уже сделанные
          бронирования по этому адресу.</p>
        <br/>
        <p>Чтобы настроить свой аккаунт в RSBA, перейдите по ссылке ниже.</p>
        <br/>
        <br/>
        <a href="$uri">
            <span class="white-elt">Подтвердить</span>
        </a>
        </body>
        </html>
    """.trimIndent()

    fun inviteRuHtml(source: SingleInviteUserReturn?, url: String) = """
        <!doctype html>
        <html class="no-js" lang="${source?.lang ?: "ru"}">
        <head>
          <meta content="text/html; charset=UTF-8" http-equiv="Content-Type">
          <title>СОЗДАТЬ АККАУНТ</title>
          <style>
            body { 
              overflow: hidden;
              padding: 20%;
            }

            .submit-button {
              min-height: 2.5rem;
              min-width: 300px;
              max-width: 400px;
              background-color: #0353e9;
              color: white;
              text-decoration: none;
              display: flex;
              flex-direction: column;
              justify-content: center;
              font-weight: bold;
              padding: .5rem;
            }

            .arrow-right {
              position: relative;
            }

            .arrow-right::before {
              position: absolute;
              top: 15px;
              right: 10px;
              background-image: url(data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZlcnNpb249IjEuMSIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiIHhtbG5zOnN2Z2pzPSJodHRwOi8vc3ZnanMuY29tL3N2Z2pzIiB3aWR0aD0iNTEyIiBoZWlnaHQ9IjUxMiIgeD0iMCIgeT0iMCIgdmlld0JveD0iMCAwIDUxMiA1MTIiIHN0eWxlPSJlbmFibGUtYmFja2dyb3VuZDpuZXcgMCAwIDUxMiA1MTIiIHhtbDpzcGFjZT0icHJlc2VydmUiPjxnPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgoJPGc+CgkJPHBhdGggZD0iTTUwNi4xMzQsMjQxLjg0M2MtMC4wMDYtMC4wMDYtMC4wMTEtMC4wMTMtMC4wMTgtMC4wMTlsLTEwNC41MDQtMTA0Yy03LjgyOS03Ljc5MS0yMC40OTItNy43NjItMjguMjg1LDAuMDY4ICAgIGMtNy43OTIsNy44MjktNy43NjIsMjAuNDkyLDAuMDY3LDI4LjI4NEw0NDMuNTU4LDIzNkgyMGMtMTEuMDQ2LDAtMjAsOC45NTQtMjAsMjBjMCwxMS4wNDYsOC45NTQsMjAsMjAsMjBoNDIzLjU1NyAgICBsLTcwLjE2Miw2OS44MjRjLTcuODI5LDcuNzkyLTcuODU5LDIwLjQ1NS0wLjA2NywyOC4yODRjNy43OTMsNy44MzEsMjAuNDU3LDcuODU4LDI4LjI4NSwwLjA2OGwxMDQuNTA0LTEwNCAgICBjMC4wMDYtMC4wMDYsMC4wMTEtMC4wMTMsMC4wMTgtMC4wMTlDNTEzLjk2OCwyNjIuMzM5LDUxMy45NDMsMjQ5LjYzNSw1MDYuMTM0LDI0MS44NDN6IiBmaWxsPSIjZmZmZmZmIiBkYXRhLW9yaWdpbmFsPSIjMDAwMDAwIiBzdHlsZT0iIj48L3BhdGg+Cgk8L2c+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPC9nPjwvc3ZnPg==);
              background-size: contain;
              display: inline-block;
              content: "";
              width: 1rem;
              height: 1rem;
            }
            
            .white-elt {
                color: blue;
            }
          </style>
        </head>

        <body>
        <h1>КЛИКНУТЬ НА ССЫЛКУ, ЧТОБЫ ЗАПОЛНИТЬ ВАШУ НАДПИСЬ</h1> 
        <p>Компания ${source?.companyName}.</p>
        <p>Нажмите кнопку "Подтвердить"</p>
        <br/>
        <p>${source?.message}</p>
        <p>Чтобы настроить свой аккаунт в RSBA, перейдите по ссылке ниже.</p>
        <br/>
        <br/>
        <a href="$url">
            <span class="white-elt">Подтвердить</span>
        </a>
        </body>
        </html>
    """.trimIndent()

    fun inviteEnHtml(source: SingleInviteUserReturn?, url: String) = """
        <!doctype html>
        <html class="no-js" lang="${source?.lang}">
        <head>
          <meta content="text/html; charset=UTF-8" http-equiv="Content-Type">
          <title>CREATE YOUR ACCOUNT</title>
          <style>
            body { 
              overflow: hidden;
              padding: 20%;
            }

            .submit-button {
              min-height: 2.5rem;
              min-width: 300px;
              max-width: 400px;
              background-color: #0353e9;
              color: white;
              text-decoration: none;
              display: flex;
              flex-direction: column;
              justify-content: center;
              font-weight: bold;
              padding: .5rem;
            }

            .arrow-right {
              position: relative;
            }

            .arrow-right::before {
              position: absolute;
              top: 15px;
              right: 10px;
              background-image: url(data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZlcnNpb249IjEuMSIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiIHhtbG5zOnN2Z2pzPSJodHRwOi8vc3ZnanMuY29tL3N2Z2pzIiB3aWR0aD0iNTEyIiBoZWlnaHQ9IjUxMiIgeD0iMCIgeT0iMCIgdmlld0JveD0iMCAwIDUxMiA1MTIiIHN0eWxlPSJlbmFibGUtYmFja2dyb3VuZDpuZXcgMCAwIDUxMiA1MTIiIHhtbDpzcGFjZT0icHJlc2VydmUiPjxnPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgoJPGc+CgkJPHBhdGggZD0iTTUwNi4xMzQsMjQxLjg0M2MtMC4wMDYtMC4wMDYtMC4wMTEtMC4wMTMtMC4wMTgtMC4wMTlsLTEwNC41MDQtMTA0Yy03LjgyOS03Ljc5MS0yMC40OTItNy43NjItMjguMjg1LDAuMDY4ICAgIGMtNy43OTIsNy44MjktNy43NjIsMjAuNDkyLDAuMDY3LDI4LjI4NEw0NDMuNTU4LDIzNkgyMGMtMTEuMDQ2LDAtMjAsOC45NTQtMjAsMjBjMCwxMS4wNDYsOC45NTQsMjAsMjAsMjBoNDIzLjU1NyAgICBsLTcwLjE2Miw2OS44MjRjLTcuODI5LDcuNzkyLTcuODU5LDIwLjQ1NS0wLjA2NywyOC4yODRjNy43OTMsNy44MzEsMjAuNDU3LDcuODU4LDI4LjI4NSwwLjA2OGwxMDQuNTA0LTEwNCAgICBjMC4wMDYtMC4wMDYsMC4wMTEtMC4wMTMsMC4wMTgtMC4wMTlDNTEzLjk2OCwyNjIuMzM5LDUxMy45NDMsMjQ5LjYzNSw1MDYuMTM0LDI0MS44NDN6IiBmaWxsPSIjZmZmZmZmIiBkYXRhLW9yaWdpbmFsPSIjMDAwMDAwIiBzdHlsZT0iIj48L3BhdGg+Cgk8L2c+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPGcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPC9nPgo8ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgo8L2c+CjxnIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjwvZz4KPC9nPjwvc3ZnPg==);
              background-size: contain;
              display: inline-block;
              content: "";
              width: 1rem;
              height: 1rem;
            }
            
            .white-elt {
                color: blue;
            }
          </style>
        </head>

        <body>
        <h1>CLICK ON THE LINK TO COMPLETE YOUR INSCRIPTION</h1> 
        <p>Company ${source?.companyName?:""}.</p>
        <p>Click the "Continue" button</p>
        <br/>
        <p>${source?.message ?: ""}</p>
        <p>To set up your RSBA account, follow the link below.</p>
        <br/>
        <br/>
        <a href="$url">
            <span class="white-elt">Continue</span>
        </a>
        </body>
        </html>
    """.trimIndent()

}