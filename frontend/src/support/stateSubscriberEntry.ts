export type stateSubscriberEntry<T> = [
  string,
  React.Dispatch<React.SetStateAction<T>>
];
