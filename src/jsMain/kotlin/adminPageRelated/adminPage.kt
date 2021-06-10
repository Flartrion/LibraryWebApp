package adminPageRelated

import adminPageRelated.balanceManagement.BalanceManagementPage
import adminPageRelated.rentManagement.RentManagementPage
import adminPageRelated.storageManagement.StorageManagementPage
import adminPageRelated.userManagement.UserManagementPage
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.css.*
import kotlinx.html.js.onClickFunction
import react.*
import react.dom.br
import styled.*

data class AdminPageState(var curLocation: AdminPageLocation) : RState

@JsExport
class AdminPage : RComponent<RProps, AdminPageState>() {
    init {
        state = AdminPageState(AdminPageLocation.MainMenu)
    }

    override fun RBuilder.render() {
        styledDiv {
            css {
                padding(top = 50.px)
                textAlign = TextAlign.center
                width = LinearDimension.fillAvailable
                minHeight = (window.outerHeight - 200).px
                height = LinearDimension.maxContent

                backgroundColor = Color("#aaaaaa")
                display = Display.inlineBlock
                fontFamily = "Arial"
                button {
                    fontSize = 24.px
                    height = 100.px
                    width = 400.px
                }
            }
            if (state.curLocation == AdminPageLocation.MainMenu) {
                styledButton {
                    attrs {
                        onClickFunction = { setState { curLocation = AdminPageLocation.Storages } }
                    }
                    +"Хранилища"
                }
                styledButton {
                    attrs {
                        onClickFunction = { setState { curLocation = AdminPageLocation.AddItem } }
                    }
                    +"Добавить книгу"
                }
                br { }
                styledButton {
                    attrs {
                        onClickFunction = { setState { curLocation = AdminPageLocation.Users } }
                    }
                    +"Пользователи"
                }
                styledButton {
                    attrs {
                        onClickFunction = { setState { curLocation = AdminPageLocation.Balance } }
                    }
                    +"Баланс"
                }
                br { }
                styledButton {
                    attrs {
                        onClickFunction = { setState { curLocation = AdminPageLocation.Rents } }
                    }
                    +"Аренды"
                }
            } else {
                css {
                    display = Display.flex
                    flexDirection = FlexDirection.column
                    alignItems = Align.stretch
                    alignContent = Align.center
                    padding(top = 0.px)
                }
                styledButton {
                    css {
                        maxWidth = 100.px
                        maxHeight = 100.px
                        alignSelf = Align.start
                    }
                    attrs {
                        onClickFunction = {
                            setState { curLocation = AdminPageLocation.MainMenu }
                            document.title = "AWAKEN, MY MASTERS!"
                        }
                    }
                    +"←"
                }
                styledDiv {
                    when (state.curLocation) {
                        AdminPageLocation.Storages -> child(StorageManagementPage::class) {

                        }
                        AdminPageLocation.AddItem -> child(AddItemPage::class) {
                            attrs {
                                onCompleteFunction = { setState { curLocation = AdminPageLocation.MainMenu } }
                            }
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
}


fun RBuilder.adminPage(): ReactElement {
    return child(AdminPage::class) {

    }
}