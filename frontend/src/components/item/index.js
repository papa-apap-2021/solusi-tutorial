import React from "react";
import DeleteIcon from '@mui/icons-material/Delete';
import AddShoppingCartIcon from '@mui/icons-material/AddShoppingCart';
import { IconButton } from "@material-ui/core";

export default function Item(props) {
  const { item, onChange, isShopList } = props;
  const { title, description, category, price, image, inCart } = item;

  const handleChange = () =>
    !!onChange && onChange({ ...item, inCart: !inCart });
  return (
    <div
      className="list-group-item list-group-item-action d-flex align-items-center justify-content-between flex-row w-100 mb-3"
    >
      <div className="d-flex">
        <div className="d-flex align-items-center">
          <img className="p-2 mr-3" src={image} alt={title} width="80"/>
        </div>
        <div>
          <div className="d-flex justify-content-between align-items-center">
            <h5 className="mb-1 font-weight-bold">
              {title}
            </h5>
          </div>
          <p className="font-weight-bold mb-0 text-md">Harga: Rp {price} k</p>
          <p className="mb-0 text-sm text-secondary"> {category}</p>
          <p className="mb-1 text-md"> {description}</p>
        </div>
      </div>
      <CustomIconButton isShopList={isShopList} inCart={inCart} handleChange={handleChange}/>
    </div>
  );
}


function CustomIconButton({ isShopList, inCart, handleChange }) {
  if (isShopList) {
    if (inCart) {
      return null;
    }
    return (
      <IconButton onClick={handleChange}>
        <AddShoppingCartIcon />
      </IconButton>);
  } else {
    return (
      <IconButton onClick={handleChange}>
        {inCart ? <DeleteIcon /> : <AddShoppingCartIcon />}
      </IconButton>);
  }
}