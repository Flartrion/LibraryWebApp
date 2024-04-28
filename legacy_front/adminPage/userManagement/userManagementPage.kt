package adminPage.userManagement

import User
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
import org.w3c.xhr.XMLHttpRequest
import react.*
import styled.*

external interface UserManagementPageState : RState {
    var isLoaded: Boolean
    var inProcess: Boolean
    var editing: Int
    var users: List<User>

    var filterOpened: Boolean
    var filterCardNum: String
    var filterName: String
    var filterPhone: String
    var filterEmail: String
}

external interface UserManagementPageProps : RProps {

}

@JsExport
class UserManagementPage : RComponent<UserManagementPageProps, UserManagementPageState>() {
    override fun componentDidMount() {
        setState {
            editing = -1

            filterCardNum = ""
            filterEmail = ""
            filterName = ""
            filterPhone = ""
        }
        val xml = XMLHttpRequest()
        xml.open("get", "/users")
        xml.onload = {
            setState {
                isLoaded = true
                users = Json.decodeFromString(xml.responseText)
            }
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
                }
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
                            filterOpened = !filterOpened
                        }
                    }
                }
                +"Фильтры"
            }

            styledForm {
                attrs {
                    method = FormMethod.post
                    id = "searchForm"
                    onSubmitFunction = {
                        it.preventDefault()
                        val getRequest = XMLHttpRequest()
                        var queryString = "/users"
                        val queryParameters = ArrayList<String>()
                        if (!state.filterCardNum.isBlank())
                            queryParameters += "card_num=${state.filterCardNum}"
                        if (!state.filterName.isBlank())
                            queryParameters += "full_name=${state.filterName}"
                        if (!state.filterPhone.isBlank())
                            queryParameters += "phone_number=${state.filterPhone}"
                        if (!state.filterEmail.isBlank())
                            queryParameters += "email=${state.filterEmail}"
                        if (queryParameters.isNotEmpty()) {
                            queryString += ("?" + queryParameters[0])
                            queryParameters.removeAt(0)
                            if (queryParameters.isNotEmpty()) {
                                for (i in 0 until queryParameters.size)
                                    queryString += "&${queryParameters[i]}"
                            }
                        }

                        getRequest.open("get", queryString)
                        getRequest.onload = {
                            setState {
                                users = Json.decodeFromString(getRequest.responseText)
                                if (!users.isEmpty())
                                    isLoaded = true
                            }
                        }
                        getRequest.send()
                    }
                }
                styledTable {
                    attrs {
                        hidden = !state.filterOpened
                    }
                    css {
                        width = LinearDimension("100%")
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
                            styledLabel { +"Номер билета: " }
                        }
                        styledTd {
                            styledInput {
                                attrs {
                                    type = InputType.text
                                    value = state.filterCardNum
                                    onChangeFunction = {
                                        setState { filterCardNum = (it.target as HTMLInputElement).value }
                                    }
                                }
                            }
                        }
                    }
                    styledTr {
                        styledTd {
                            styledLabel { +"Телефон: " }
                        }
                        styledTd {
                            styledInput {
                                attrs {
                                    type = InputType.text
                                    value = state.filterPhone
                                    onChangeFunction = {
                                        setState { filterPhone = (it.target as HTMLInputElement).value }
                                    }
                                }
                            }
                        }
                    }

                    styledTr {
                        styledTd {
                            styledLabel { +"Электронная почта: " }
                        }
                        styledTd {
                            styledInput {
                                attrs {
                                    type = InputType.text
                                    value = state.filterEmail
                                    onChangeFunction = {
                                        setState { filterEmail = (it.target as HTMLInputElement).value }
                                    }
                                }
                            }
                        }
                    }

                    styledTr {
                        styledTd {
                            styledLabel { +"Имя: " }
                        }
                        styledTd {
                            styledInput {
                                attrs {
                                    form = "searchForm"
                                    name = "type"
                                    value = state.filterName
                                    onChangeFunction = {
                                        setState { filterName = (it.target as HTMLInputElement).value }
                                    }
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
                                    form = "searchForm"
                                    name = "submit"
                                    value = "Подтвердить"
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

            if (state.isLoaded) {
                styledTable {
                    css {
                        borderCollapse = BorderCollapse.collapse
                        button {
                            display = Display.inlineBlock
                            fontSize = 14.px
                            width = LinearDimension.auto
                            margin(left = 5.px, right = 5.px)
                        }
                        tr {
                            td {
                                borderStyle = BorderStyle.solid
                                borderColor = Color("#777777")
                            }
                            th {
                                textAlign = TextAlign.center
                                borderStyle = BorderStyle.solid
                                borderColor = Color("#777777")
                            }
                        }
                    }
                    styledColGroup {
                        styledCol {
                            css {
                                width = LinearDimension("25%")
                            }
                        }
                        styledCol { }
                    }
                    styledTr {
                        styledTh {
                            +"Номер билета"
                        }
                        styledTh {
                            +"Полное имя"
                        }
                    }
                    for (i in 0 until state.users.size) {
                        userListElement {
                            editState = state.editing == i
                            processState = state.inProcess
                            user = state.users[i]

                            changeEditing = {
                                setState {
                                    editing = if (it) i else -1
                                }
                            }
                            changeInProcess = {
                                setState {
                                    processState = it
                                }
                            }
                            updateFun = {
                                setState {
                                    editing = -1
                                }
                                val updateRequest = XMLHttpRequest()
                                updateRequest.open("get", "/users")
                                updateRequest.onload = {
                                    setState {
                                        users = Json.decodeFromString(updateRequest.responseText)
                                        inProcess = false
                                    }
                                }
                                updateRequest.send()
                            }
                        }
                    }
                }
            } else {
                styledP { +"Mmmm, no, this is unwise" }
            }

            userAddMenu {
                changeInProcess = {
                    setState { inProcess = it }
                }

                update = {
                    setState {
                        editing = -1
                    }
                    val updateRequest = XMLHttpRequest()
                    updateRequest.open("get", "/users")
                    updateRequest.onload = {
                        setState {
                            users = Json.decodeFromString(updateRequest.responseText)
                            inProcess = false
                        }
                    }
                    updateRequest.send()
                }
            }
        }
    }
}
