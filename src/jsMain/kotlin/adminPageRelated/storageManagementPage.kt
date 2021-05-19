package adminPageRelated

import kotlinx.css.*
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import org.w3c.xhr.XMLHttpRequest
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import styled.*

data class StorageManagementPageState(val isAddMenuVisible: Boolean) : RState

external interface StorageManagementPageProps : RProps {
    var storageAddress: String
    var storageContactNumber: String
}

class StorageManagementPage : RComponent<StorageManagementPageProps, StorageManagementPageState>() {
    init {
        state = StorageManagementPageState(false)
    }

    override fun RBuilder.render() {
        +"Some content here"
        styledTable {
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
                            value = "Let's goooo"
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
