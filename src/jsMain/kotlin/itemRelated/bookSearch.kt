package itemRelated

import Items
import kotlinx.browser.window
import kotlinx.css.*
import kotlinx.html.FormMethod
import kotlinx.html.InputType
import kotlinx.html.hidden
import kotlinx.html.id
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import kotlinx.html.js.onSubmitFunction
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLSelectElement
import org.w3c.xhr.XMLHttpRequest
import react.*
import react.dom.option
import styled.*

external interface BookSearchState : RState {
    var isSearchVisible: Boolean
    var titleInput: String
    var authorsInput: String
    var typeInput: String
    var isbnInput: String
    var rlbcInput: String
    var ascDesc: Boolean
    var sorting: String
    var bookList: List<Items>
    var listLoaded: Boolean
    var editing: Int
    var inProcess: Boolean
}

//data class itemRelated.BookSearchState(
//    var isSearchVisible: Boolean = false,
//    var titleInput: String = "",
//    var authorsInput: String = "",
//    var typeInput: String = "",
//    var isbnInput: String = "",
//    var rlbcInput: String = "",
//    var ascDesc: Boolean = false,
//    var sorting: String = "Alphabetic",
//    var bookList: List<Items> = ArrayList(),
//    var listLoaded: Boolean = false,
//    var editing: Int = -1,
//    var inProcess: Boolean = false
//) : RState

external interface BookSearchProps : RProps {

}

@JsExport
class BookSearch : RComponent<BookSearchProps, BookSearchState>() {

    override fun componentDidMount() {
        setState {
            titleInput = ""
            authorsInput = ""
            typeInput = ""
            isbnInput = ""
            rlbcInput = ""
            ascDesc = false
            listLoaded = false
            inProcess = false
            isSearchVisible = false
            editing = -1
            sorting = "Alphabetic"
        }
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
                        setState {
                            isSearchVisible = !isSearchVisible
                        }
                    }
                }
                +"Search"
            }

            styledForm {
                attrs {
                    method = FormMethod.post
                    id = "searchForm"
                    onSubmitFunction = {
                        it.preventDefault()
                        val getRequest = XMLHttpRequest()
                        var queryString = "/items?ascDesc=${if (state.ascDesc) "ASC" else "DESC"}"
                        if (!state.titleInput.isBlank())
                            queryString += "&title=${state.titleInput}"
                        if (!state.authorsInput.isBlank())
                            queryString += "&authors=${state.authorsInput}"
                        if (!state.typeInput.isBlank())
                            queryString += "&type=${state.typeInput}"
                        if (!state.isbnInput.isBlank())
                            queryString += "&isbn=${state.isbnInput}"
                        if (!state.rlbcInput.isBlank())
                            queryString += "&rlbc=${state.rlbcInput}"
                        getRequest.open("get", queryString)
                        getRequest.onload = {
                            setState {
                                bookList = Json.decodeFromString(getRequest.responseText)
                                if (!bookList.isEmpty())
                                    listLoaded = true
                            }
                        }
                        getRequest.send()
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
                                    value = state.titleInput
                                    onChangeFunction = {
                                        val newValue = (it.target as HTMLInputElement).value
                                        setState { titleInput = newValue }
                                    }
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
                                    type = InputType.text
                                    value = state.authorsInput
                                    onChangeFunction = {
                                        val newValue = (it.target as HTMLInputElement).value
                                        setState { authorsInput = newValue }
                                    }
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
                                    value = state.typeInput
                                    onChangeFunction = {
                                        val newValue = (it.target as HTMLInputElement).value
                                        setState { typeInput = newValue }
                                    }
                                }
                            }
                        }
                    }

                    styledTr {
                        styledTd {
                            styledLabel { +"ISBN: " }
                        }
                        styledTd {
                            styledInput {
                                attrs {
                                    form = "searchForm"
                                    name = "isbn"
                                    value = state.isbnInput
                                    onChangeFunction = {
                                        val newValue = (it.target as HTMLInputElement).value
                                        setState { isbnInput = newValue }
                                    }
                                }
                            }
                        }
                    }

                    styledTr {
                        styledTd {
                            styledLabel { +"ББК: " }
                        }
                        styledTd {
                            styledInput {
                                attrs {
                                    form = "searchForm"
                                    name = "rlbc"
                                    value = state.rlbcInput
                                    onChangeFunction = {
                                        val newValue = (it.target as HTMLInputElement).value
                                        setState { rlbcInput = newValue }
                                    }
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
                                    padding(left = 5.px, right = 5.px, bottom = 2.px)
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
                                    value = state.sorting
                                    onChangeFunction = {
                                        val newValue = (it.target as HTMLSelectElement).value
                                        setState { sorting = newValue }
                                    }
                                }
                                option {
                                    attrs {
                                        value = "Alphabetic"
                                        selected = true
                                    }
                                    +"По наименованию"
                                }
//                                option {
//                                    attrs {
//                                        value = "ByDateReleased"
//                                    }
//                                    +"По дате выпуска"
//                                }
//                                option {
//                                    attrs {
//                                        value = "ByDateAdded"
//                                        selected = true
//                                    }
//                                    +"По дате добавления"
//                                }
                            }
                            styledB {
                                attrs {
                                    onClickFunction = {
                                        setState { ascDesc = !ascDesc }
                                    }
                                }
                                if (state.ascDesc)
                                    +"↓" else
                                    +"↑"
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

            if (state.listLoaded) {
                for (i in 0 until state.bookList.size) {
                    itemListElement {
                        item = state.bookList[i]
                        editing = (state.editing == i)
                        inProcess = state.inProcess
                        changeInProcess = {
                            setState { inProcess = it }
                        }
                        changeEditing = {
                            setState { editing = if (it) i else -1 }
                        }
                        update = {
                            val getRequest = XMLHttpRequest()
                            var queryString = "/items?ascDesc=${if (state.ascDesc) "ASC" else "DESC"}"
                            if (!state.titleInput.isBlank())
                                queryString += "&title=${state.titleInput}"
                            if (!state.authorsInput.isBlank())
                                queryString += "&authors=${state.authorsInput}"
                            if (!state.typeInput.isBlank())
                                queryString += "&type=${state.typeInput}"
                            if (!state.isbnInput.isBlank())
                                queryString += "&isbn=${state.isbnInput}"
                            if (!state.rlbcInput.isBlank())
                                queryString += "&rlbc=${state.rlbcInput}"
                            getRequest.open("get", queryString)
                            getRequest.onload = {
                                setState {
                                    inProcess = false
                                    bookList = Json.decodeFromString(getRequest.responseText)
                                    if (!bookList.isEmpty())
                                        listLoaded = true
                                }
                            }
                            getRequest.send()
                        }
                    }
                }
            } else {
                +"Nothing here just yet."
            }
        }
    }
}


fun RBuilder.generalBody(): ReactElement {
    return child(BookSearch::class) {

    }
}