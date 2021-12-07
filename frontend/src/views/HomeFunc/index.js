import listItems from "../../items.json";
import List from "../../components/list/index";
import React, { useState } from "react";
import "./index.css";
import Badge from "@material-ui/core/Badge";
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import { Fab } from "@material-ui/core";
import ViewStreamIcon from '@mui/icons-material/ViewStream';

/**
 * Building React component using functional programming paradigm
 */
function App() {
    // Btw, this is hooks. useState function returns an array
    // contains the state and a function to set the state -> [state, setState].
    // What you see below is array destruction.
    // Let say you have an array const arr = ["aaa", "bbb"], to access the item
    // we can use index arr[0] OR destruct it like below
    // const [varName, index1] = arr, variable varName is guaranteed to get the value of index 0 OR "aaa"
    // here is the illustration for this situation
    // below is the return value of useState
    // [favItems, setFavItems] = [state, setState]

    // const [favItems, setFavItems] = useState(() => []);
    // const [hideDelButton, sethideDelButton] = useState(true);
    // const [favHidden, setFavHidden] = useState(false);

    const [shopItems, setShopItems] = useState(() => listItems);
    const [cartItems, setCartItems] = useState(() => []);
    const [hideDelButton, sethideDelButton] = useState(true);
    const [cartHidden, setCartHidden] = useState(true);
    const [balance, setBalance] = useState(120);

    function resetShopItem(inCartItems){
        const tempShopItems = [...shopItems];
        let total = 0;
        for (let i = 0; i < inCartItems.length; i++) {
            const item = inCartItems[i];
            const targetInd = tempShopItems.findIndex((it) => it.id === item.id);
            tempShopItems[targetInd].inCart = false;
            total = total + tempShopItems[targetInd].price;
        }
        increaseBalance(total);
        setShopItems(tempShopItems);
    }

    function updateShopItem(item, inCart){
        const tempShopItems = [...shopItems];
        const targetInd = tempShopItems.findIndex((it) => it.id === item.id);
        tempShopItems[targetInd].inCart = inCart;
        if(inCart == true) decreaseBalance(tempShopItems[targetInd].price);
        else increaseBalance(tempShopItems[targetInd].price);
        setShopItems(tempShopItems);
    }

    function handleToggle(){
        setCartHidden(!cartHidden);
    }

    function handleAddItemToCart(item){
        const newItems = [...cartItems];
        const newItem = { ...item };
        const targetInd = newItems.findIndex((it) => it.id === newItem.id);

        if (targetInd < 0) {
            if(balance - newItem.price > 0){
                newItem.inCart = true;
                newItems.push(newItem);
                updateShopItem(newItem, true)
            }else{
                triggerAlert();
            }
        }

        if (newItems.length === 0) {
            sethideDelButton(true);
        } else {
            sethideDelButton(false);
        }
        setCartItems(newItems);
    }

    function handleDeleteItemFromCart(item){
        const newItems = [...cartItems];
        const newItem = { ...item };
        const targetInd = newItems.findIndex((it) => it.id === newItem.id);
        newItems.splice(targetInd, 1);
        updateShopItem(newItem, false);

        if (newItems.length === 0) {
            sethideDelButton(true);
        } else {
            sethideDelButton(false);
        }
        setCartItems(newItems);
    }

    function deleteCartItem(){
        resetShopItem(cartItems);
        setCartItems([]);
        sethideDelButton(true);
    }

    function increaseBalance(amount){
        setBalance(balance + amount);
    }

    function decreaseBalance(amount){
        setBalance(balance - amount);
    }

    function triggerAlert(){
        alert("Balance not sufficient!");
    }

    return (
        <div className="container-fluid">
            <h1 className="text-center mt-3 mb-0">Mini Commerce</h1>
            <div style={{ position: "fixed", top: 25, right: 25 }}>
                <Fab variant="extended" onClick={handleToggle}>
                    {cartHidden ?
                        <Badge color="secondary" badgeContent={cartItems.length}>
                            <ShoppingCartIcon />
                        </Badge>
                        : <ViewStreamIcon/>}
                </Fab>
            </div>
            <p className="text-center text-secondary text-sm font-italic">
                (this is a <strong>function-based</strong> application)
            </p>
            <p className="text-center text-primary" >Your Balance: <b> {balance}</b> </p>
            <div className="container pt-3">
                {!cartHidden ?
                    <div className="row">
                        <div className="col-sm text-center">
                            <button
                                hidden={hideDelButton}
                                className="btn btn-outline-danger"
                                onClick={deleteCartItem}
                            >
                                Hapus Cart
                            </button>
                        </div>
                    </div>
                    : null}
                <div className="row mt-3">
                    {!cartHidden ? (
                        <div className="col-sm">
                            <List
                                title="My Cart"
                                items={cartItems}
                                onItemClick={handleDeleteItemFromCart}
                                isShopList={false}
                            ></List>
                        </div>
                    ) : <div className="col-sm">
                        <List
                            title="List Items"
                            items={shopItems}
                            onItemClick={handleAddItemToCart}
                            isShopList={true}
                        ></List>
                    </div>}
                </div>
            </div>
        </div>
    );

    // return (
    //     <div className="container-fluid">
    //         <h1 className="text-center mt-3 mb-0">Favorites Movie App</h1>
    //         <p className="text-center text-secondary text-sm font-italic">
    //             (This is a <strong>function-based</strong> application)
    //         </p>
    //         <div className="container pt-3">
    //             <div className="row">
    //                 <div className="col-sm text-center">
    //                     <div className="form-check">
    //                         <input
    //                             type="checkbox"
    //                             checked={favHidden ? true : false}
    //                             onChange={handleToggle}
    //                             className="form-check-input"
    //                             id="exampleCheck1"
    //                         />
    //                         <label className="form-check-label" htmlFor="exampleCheck1">
    //                             Hide Favorites
    //                         </label>
    //                     </div>
    //                     <button
    //                         hidden={hideDelButton}
    //                         className="btn btn-outline-danger"
    //                         onClick={deleteFav}
    //                     >
    //                         Hapus Favorite
    //                     </button>
    //                 </div>
    //             </div>
    //             <div className="row mt-3">
    //                 <div className="col-sm">
    //                     <List
    //                         title="List Items"
    //                         items={listItems}
    //                         onItemClick={handleAddItemToCart}
    //                     />
    //                 </div>
    //                 {!favHidden ? (
    //                     <div className="col-sm">
    //                         <List
    //                             title="My Favorites"
    //                             items={favItems}
    //                             onItemClick={handleDeleteItemFromCart}
    //                         ></List>
    //                     </div>
    //                 ) : null}
    //             </div>
    //         </div>
    //     </div>
    // );
}
export default App;
