package adminPageRelated

import adminPageRelated.storageManagement.StorageManagementPage
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.css.*
import kotlinx.html.js.onClickFunction
import react.*
import react.dom.br
import styled.*

data class AdminPageState(var curLocation: AdminPageLocation) : RState

external interface AdminPageProps : RProps


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
                    fontSize = 30.px
                    height = 100.px
                    width = 400.px
                    borderStyle = BorderStyle.none
                    borderRadius = 0.px
                    backgroundColor = Color("#999999")
                    color = Color.white
                    hover {
                        backgroundColor = Color("#bbbbbb")
                        color = Color.darkRed
                    }
                }
            }
            if (state.curLocation == AdminPageLocation.MainMenu) {
                styledButton {
                    attrs {
                        onClickFunction = { setState { curLocation = AdminPageLocation.ManageStorages } }
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
                    +"Кнопка один"
                }
                styledButton {
                    +"Кнопка два"
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
                        AdminPageLocation.ManageStorages -> child(StorageManagementPage::class) {

                        }
                        AdminPageLocation.AddItem -> child(AddItemPage::class) {
                            attrs {
                                onCompleteFunction = { setState { curLocation = AdminPageLocation.MainMenu } }
                            }
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