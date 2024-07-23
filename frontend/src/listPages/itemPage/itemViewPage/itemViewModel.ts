import Item from "../../../dataclasses/item";

type ItemViewModel = {
  item: Item;
  itemIndex: number;
};

const itemViewModel: ItemViewModel = {
  item: undefined,
  itemIndex: undefined,
};

export default itemViewModel;
