package adminPage

import adminPage.balanceManagement.BalanceManagementPage
import adminPage.rentManagement.RentManagementPage
import adminPage.storageManagement.StorageManagementPage
import adminPage.userManagement.UserManagementPage
import emotion.react.css
import kotlinx.browser.document
import kotlinx.browser.window
import react.FC
import react.Props
import react.dom.html.ReactHTML.br
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.useState
import web.cssom.*


fun AdminPage() = FC<Props> {
    var curLocation: AdminPageLocation by useState(AdminPageLocation.MainMenu)

    div {
        css {
            paddingTop = 50.px
            textAlign = TextAlign.center
            width = 100.pc
            minHeight = (window.outerHeight - 200).px
            height = 100.pc

            backgroundColor = Color("#aaaaaa")
            display = Display.inlineBlock
            fontFamily = FontFamily.serif
            button {
                fontSize = 24.px
                height = 100.px
                width = 400.px
            }
        }
        if (curLocation == AdminPageLocation.MainMenu) {
            button {
                onClick = { curLocation = AdminPageLocation.Storages }
                +"Хранилища"
            }
            button {
                onClick = { curLocation = AdminPageLocation.AddItem }
                +"Добавить книгу"
            }
            br { }
            button {
                onClick = { curLocation = AdminPageLocation.Users }

                +"Пользователи"
            }
            button {
                onClick = { curLocation = AdminPageLocation.Balance }

                +"Баланс"
            }
            br { }
            button {
                onClick = { curLocation = AdminPageLocation.Rents }

                +"Аренды"
            }
        } else {
            css {
                display = Display.flex
                flexDirection = FlexDirection.column
                alignItems = AlignItems.stretch
                alignContent = AlignContent.center
                paddingTop = 0.px
            }
            button {
                css {
                    maxWidth = 100.px
                    maxHeight = 100.px
                    alignSelf = AlignSelf.start
                }
                onClick = {
                    curLocation = AdminPageLocation.MainMenu
                    document.title = "AWAKEN, MY MASTERS!"
                }
                +"←"
            }
            div {
                when (curLocation) {
                    AdminPageLocation.Storages -> child(StorageManagementPage::class) {

                    }

                    AdminPageLocation.AddItem -> AddItemPage {
                        onCompleteFunction = { curLocation = AdminPageLocation.MainMenu }
                    }

                    AdminPageLocation.Users -> child(UserManagementPage::class) {
                    }

                    AdminPageLocation.Balance -> child(BalanceManagementPage::class) {
                    }

                    AdminPageLocation.Rents -> child(RentManagementPage::class) {
                    }

                    else -> {
                    }
                }
            }
        }
    }
}