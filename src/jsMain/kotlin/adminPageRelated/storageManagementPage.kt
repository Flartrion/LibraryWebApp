package adminPageRelated

import Storages
import kotlinx.css.*
import kotlinx.css.properties.Time
import kotlinx.css.properties.Timing
import kotlinx.css.properties.Transition
import kotlinx.html.InputType
import kotlinx.html.hidden
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import org.w3c.dom.Storage
import org.w3c.dom.XMLDocument
import org.w3c.xhr.XMLHttpRequest
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.br
import styled.*
import kotlin.js.Json

data class StorageManagementPageState(var isAddMenuVisible: Boolean, var storages: ArrayList<Storages>) : RState

external interface StorageManagementPageProps : RProps {
    var storageAddress: String
    var storageContactNumber: String
}

class StorageManagementPage : RComponent<StorageManagementPageProps, StorageManagementPageState>() {
    init {
        state = StorageManagementPageState(false, ArrayList())

    }

    override fun componentDidMount() {
        val xml = XMLHttpRequest()
        xml.open("get", "/storages")
        xml.onload = {
            setState(StorageManagementPageState(false, JSON.parse(xml.responseText) as ArrayList<Storages>))
        }
        xml.send()
    }

    override fun RBuilder.render() {
        styledDiv {
            css {
                display = Display.flex
                flexDirection = FlexDirection.column
                button {
                    width = LinearDimension.fillAvailable
                    height = 50.px
                    fontSize = 17.pt
                    borderRadius = 0.px
                    borderStyle = BorderStyle.none
                    color = Color.white
                    backgroundColor = Color("#999999")
                    hover {
                        backgroundColor = Color("#aaaaaa")
                        color = Color.darkRed
                    }
                }
            }
            if (state.storages.isEmpty()) {
                styledP { +"You fool" }
            } else {
                styledP { +"Mmmm, yes, this is wise" }
                for (storage in state.storages) {
                    styledP { storage.id_storage }
                    +storage.address
                }
            }
            styledButton {
                attrs {
                    onClickFunction = {
                        setState(StorageManagementPageState(!state.isAddMenuVisible, state.storages))
                    }
                }
                +"Добавить хранилище"
            }
            styledTable {
                attrs {
                    hidden = !state.isAddMenuVisible
                }
                css {
                    width = LinearDimension.fillAvailable
                    backgroundColor = Color("#888888")
                    children {
                        children {
                            textAlign = TextAlign.left
                            firstChild {
                                textAlign = TextAlign.right
                            }
                            input {
                                width = 500.px
                            }
                        }
                        lastChild {
                            children {
                                textAlign = TextAlign.center
                            }
                        }
                    }
                }
                styledColGroup {
                    styledCol {
                        css {
                            width = LinearDimension("30%")
                            textAlign = TextAlign.right
                        }
                    }
                    styledCol {
                        css {
                            textAlign = TextAlign.left
                        }
                    }
                }
                styledTr {
                    styledTd {
                        styledLabel { +"Адрес: " }
                    }
                    styledTd {
                        styledInput {
                            attrs {
                                onChangeFunction = {
                                    props.storageAddress = value
                                }
                                type = InputType.text
                            }
                        }
                    }
                }
                styledTr {
                    styledTd {
                        styledLabel { +"Контактный номер: " }
                    }
                    styledTd {
                        styledInput {
                            attrs {
                                onChangeFunction = {
                                    props.storageContactNumber = value
                                }
                                type = InputType.text
                            }
                        }
                    }
                }

                styledTr {
                    styledTd {
                        attrs {
                            colSpan = "2"
                        }
                        styledInput {
                            attrs {
                                value = "Подтвердить"
                                type = InputType.submit
                                onClickFunction = {
                                    val request = XMLHttpRequest()
                                    request.open("post", "/")
                                    request.addEventListener("load", {
                                        console.info(request.response)
                                    })
                                    request.send()
                                }
                            }
                            css {
                                borderRadius = 0.px
                                fontFamily = "Arial"
                                borderStyle = BorderStyle.none
                                backgroundColor = Color("#999999")
                                color = Color.white
                                hover {
                                    backgroundColor = Color("#cccccc")
                                    color = Color.darkRed
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
