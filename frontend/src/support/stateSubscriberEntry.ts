/** Good generic tuple for storing name: setState function pairs. Pretty genius, if I say so myself */
type stateSubscriberEntry<T> = [string, React.Dispatch<React.SetStateAction<T>>]
export default stateSubscriberEntry
