import dateDiffInDays from "../../../support/dateDiff"
import UserActionEnum from "./userActionEnum"

const userReducerFabric = (attachedModel: any) => {
  const reducer: React.Reducer<any, any> = (state: any, action: any) => {
    const [actionName, payload]: [UserActionEnum, any] = action
    const newState = { ...state }
    switch (actionName) {
      case UserActionEnum.id:
        if (payload.length <= 36) {
          newState.errField = undefined
          newState.id = payload
          if (attachedModel != null) attachedModel.id = payload
        } else newState.errField = actionName
        return newState
      case UserActionEnum.fullName:
        if (payload.length <= 50) {
          newState.errField = undefined
          newState.fullName = payload
          if (attachedModel != null) attachedModel.fullName = payload
        } else newState.errField = actionName
        return newState
      case UserActionEnum.dob:
        // console.log(payload);
        // console.log(new Date(Date.parse(payload)));
        const dateDiff = dateDiffInDays(
          new Date(),
          new Date(Date.parse(payload))
        )
        // console.log(dateDiff);
        if (150 * 365 > dateDiff || dateDiff > 12 * 365) {
          newState.errField = undefined
          newState.dob = payload
          if (attachedModel != null) attachedModel.dob = payload
        } else {
          // console.error("trigger error?")
          newState.errField = actionName
          newState.dob = payload
          if (attachedModel != null) attachedModel.dob = payload
        }
        return newState
      case UserActionEnum.email:
        if (payload.length <= 40) {
          newState.errField = undefined
          newState.email = payload
          if (attachedModel != null) attachedModel.email = payload
        } else newState.errField = actionName
        return newState
      case UserActionEnum.phoneNumber:
        if (payload.length <= 15) {
          newState.errField = undefined
          newState.phoneNumber = payload
          if (attachedModel != null) attachedModel.phoneNumber = payload
        } else newState.errField = actionName
        return newState
      case UserActionEnum.role:
        const parseVal = Number.parseInt(payload)
        if (parseVal > 0 || payload == "") {
          newState.errField = undefined
          newState.role = payload
          if (attachedModel != null) attachedModel.role = payload
        } else newState.errField = actionName
        return newState
      case UserActionEnum.processing:
        newState.processing = payload
        return newState
      default:
        console.log(
          "Unknown action: " + actionName + "! Reverting to previous state."
        )
        return state
    }
  }
  return reducer
}

export default userReducerFabric
