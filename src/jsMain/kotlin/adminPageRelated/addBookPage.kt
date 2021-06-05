package adminPageRelated

import kotlinx.css.*
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import org.w3c.dom.ImageBitmap
import org.w3c.xhr.XMLHttpRequest
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import styled.*

external interface AddBookPageProps : RProps {
    var bookName: String
    var authorName: String
    var bookType: String
    var image: ImageBitmap
}

class AddBookPage : RComponent<AddBookPageProps, RState>() {
    override fun RBuilder.render() {
        styledTable {
            css {
                width = LinearDimension.fillAvailable
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
                    styledLabel { +"Заглавие: " }
                }
                styledTd {
                    styledInput {
                        attrs {
                            onChangeFunction = {
                                props.bookName = value
                            }
                            type = InputType.text
                        }
                    }
                }
            }
            styledTr {
                styledTd {
                    styledLabel { +"Авторы: " }
                }
                styledTd {
                    styledInput {
                        attrs {
                            onChangeFunction = {
                                props.authorName = value
                            }
                            type = InputType.text
                        }
                    }
                }
            }

            styledTr {
                styledTd {
                    styledLabel { +"Тип: " }
                }
                styledTd {
                    styledInput {
                        attrs {
                            onChangeFunction = {
                                props.bookType = value
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