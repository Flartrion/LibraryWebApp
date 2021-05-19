import kotlinx.browser.window
import kotlinx.css.*
import kotlinx.css.properties.*
import kotlinx.html.InputType
import kotlinx.html.hidden
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import org.w3c.xhr.XMLHttpRequest
import react.*
import react.dom.br
import styled.*


data class BookSearchState(val isSearchVisible: Boolean) : RState

external interface BookSearchProps : RProps {
    var bookName: String?
    var authorName: String?
    var bookType: String?
}

@JsExport
class BookSearch : RComponent<BookSearchProps, BookSearchState>() {
    init {
        state = BookSearchState(false)
    }

    override fun RBuilder.render() {
        styledDiv {
            css {
                textAlign = TextAlign.center
                width = LinearDimension.fillAvailable
                minHeight = (window.outerHeight - 200).px
                height = LinearDimension.maxContent

//                padding(30.px)
                backgroundColor = Color("#aaaaaa")
                display = Display.inlineBlock
                fontFamily = "Arial"
            }
            styledDiv {
                css {
                    padding(top = 10.px, bottom = 10.px)
                    backgroundColor = Color("#cccccc")
                    textAlign = TextAlign.center
                    cursor = Cursor.pointer
                    userSelect = UserSelect.none
                }
                attrs {
                    onClickFunction = {
                        setState(BookSearchState(!state.isSearchVisible))
                    }
                }
                +"Search"
            }
            styledTable {
                attrs {
                    hidden = !state.isSearchVisible
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

fun RBuilder.generalBody(): ReactElement {
    return child(BookSearch::class) {

    }
}