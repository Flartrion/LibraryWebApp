type Storage = Id & {
  address: string;
};

export default Storage;

export function newStorage(id?: string, address?: string) {
  const retval: Storage = {} as Storage;
  retval.id = id != undefined ? id : "";
  retval.address = address != undefined ? address : "";
  return retval;
}
