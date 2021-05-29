import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.css.*
import kotlinx.css.properties.*
import kotlinx.html.*
import kotlinx.html.js.*
import org.w3c.xhr.XMLHttpRequest
import react.*
import react.dom.br
import react.dom.option
import styled.*


data class BookSearchState(val isSearchVisible: Boolean, val ascDesc: Boolean) : RState

external interface BookSearchProps : RProps {
    var bookName: String
    var authorName: String
    var bookType: String
    var searchSorting: SearchSorting
}

@JsExport
class BookSearch : RComponent<BookSearchProps, BookSearchState>() {
    init {
        state = BookSearchState(isSearchVisible = false, ascDesc = false)
    }

    override fun componentDidMount() {
        props.bookName = ""
        props.authorName = ""
        props.bookType = ""
        props.searchSorting = SearchSorting.ByDateReleased
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
                        setState(BookSearchState(!state.isSearchVisible, state.ascDesc))
                    }
                }
                +"Search"
            }
            styledForm {
                attrs {
                    action = "/search"
                    method = FormMethod.post
                    id = "searchForm"
//                    target = "_blank"
                    onSubmitFunction = {
                        it.preventDefault()
                    }
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
                                    form = "searchForm"
                                    name = "name"
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
                                    form = "searchForm"
                                    name = "authors"
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
                                    form = "searchForm"
                                    name = "type"
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
                            styledLabel { +"Сортировать: " }
                        }
                        styledTd {
                            css {
                                select {
                                    width = LinearDimension("200px")
                                }
                                b {
                                    userSelect = UserSelect.none
                                    padding(left = 5.px, right = 5.px)
                                    borderStyle = BorderStyle.solid
                                    borderWidth = 1.px
                                    backgroundColor = Color("#999999")
                                    color = Color.white
                                    hover {
                                        backgroundColor = Color("#cccccc")
                                        color = Color.darkRed
                                    }
                                }
                            }
                            styledSelect {
                                attrs {
                                    form = "searchForm"
                                    name = "sorting"
                                }
                                option {
                                    attrs {
                                        value = "Alphabetic"
                                    }
                                    +"По наименованию"
                                }
                                option {
                                    attrs {
                                        value = "ByDateReleased"
                                    }
                                    +"По дате выпуска"
                                }
                                option {
                                    attrs {
                                        value = "ByDateAdded"
                                        selected = true
                                    }
                                    +"По дате добавления"
                                }
                            }
                            styledInput {
                                attrs {
                                    form = "searchForm"
                                    name = "ascDesc"
                                    hidden = true
                                    type = InputType.checkBox
                                    checked = state.ascDesc
                                }
                            }
                            styledB {
                                attrs {
                                    onClickFunction = {
                                        setState(BookSearchState(true, !state.ascDesc))
                                    }
                                }
                                if (state.ascDesc)
                                    +"↑" else
                                    +"↓"
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
                                    form = "searchForm"
                                    name = "submit"
                                    value = "Let's goooo"
                                    type = InputType.submit
//                                onClickFunction = {
//                                    val request = XMLHttpRequest()
//                                    request.open("post", "/")
//                                    request.addEventListener("load", {
//
//                                    })
//                                    request.send()
//                                }
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
}

fun RBuilder.generalBody(): ReactElement {
    return child(BookSearch::class) {

    }
}