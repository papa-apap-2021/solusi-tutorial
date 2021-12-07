import React from 'react';
import listItems from "../../items.json";
import List from "../../components/list/index";
import "./index.css";
import Badge from "@material-ui/core/Badge";
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import { Fab } from "@material-ui/core";
import ViewStreamIcon from '@mui/icons-material/ViewStream';

export default class Home extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            shopItems: listItems,
            cartItems: [],
            hideDelButton: true,
            cartHidden: true,
            balance: 120,
        };
    }

    resetShopItem = (inCartItems) => {
        const tempShopItems = this.state.shopItems;
        let total = 0;
        for (let i = 0; i < inCartItems.length; i++) {
            const item = inCartItems[i];
            const targetInd = tempShopItems.findIndex((it) => it.id === item.id);
            tempShopItems[targetInd].inCart = false;
            total = total + tempShopItems[targetInd].price;
        }
        this.increaseBalance(total);
        this.setState({ shopItems: tempShopItems });
    }

    updateShopItem = (item, inCart) => {
        const tempShopItems = this.state.shopItems;
        const targetInd = tempShopItems.findIndex((it) => it.id === item.id);
        tempShopItems[targetInd].inCart = inCart;
        if(inCart == true) this.decreaseBalance(tempShopItems[targetInd].price);
        else this.increaseBalance(tempShopItems[targetInd].price);
        this.setState({ shopItems: tempShopItems });
    }

    handleToggle = () => {
        const cartHidden = this.state.cartHidden;
        this.setState({ cartHidden: !cartHidden });
    };

    increaseBalance(amount){
        this.setState({ balance : this.state.balance+amount });
    }

    decreaseBalance(amount){
        this.setState({ balance : this.state.balance-amount });
    }

    triggerAlert(){
        alert("Balance not sufficient!");
    }

    handleAddItemToCart = (item) => {
        const newItems = [...this.state.cartItems];
        const newItem = { ...item };
        const targetInd = newItems.findIndex((it) => it.id === newItem.id);
        if (targetInd < 0) {
            if (this.state.balance - newItem.price > 0) {
                newItem.inCart = true;
                newItems.push(newItem);
                this.updateShopItem(newItem, true)
            } else {
                this.triggerAlert();
            }
        }

        if (newItems.length === 0) {
            this.setState({ hideDelButton: true });
        } else {
            this.setState({ hideDelButton: false });
        }
        this.setState({ cartItems: newItems });
    };

    handleDeleteItemFromCart = (item) => {
        const newItems = [...this.state.cartItems];
        const newItem = { ...item };
        const targetInd = newItems.findIndex((it) => it.id === newItem.id);
        newItems.splice(targetInd, 1); 
        this.updateShopItem(newItem, false);

        if (newItems.length === 0) {
            this.setState({ hideDelButton: true });
        } else {
            this.setState({ hideDelButton: false });
        }
        this.setState({ cartItems: newItems });
    };

    deleteCartItem = () => {
        this.resetShopItem(this.state.cartItems);
        this.setState({ cartItems: [], hideDelButton: true });
    };

    render() {
        return (
            <div className="container-fluid">
                <h1 className="text-center mt-3 mb-0">Mini Commerce</h1>
                <div style={{ position: "fixed", top: 25, right: 25 }}>
                    <Fab variant="extended" onClick={this.handleToggle}>
                        {this.state.cartHidden ?
                            <Badge color="secondary" badgeContent={this.state.cartItems.length}>
                                <ShoppingCartIcon />
                            </Badge>
                            : <ViewStreamIcon/>}
                    </Fab>
                </div>
                <p className="text-center text-secondary text-sm font-italic">
                    (this is a <strong>class-based</strong> application)
                </p>
                <p className="text-center text-primary" >Your Balance: <b> {this.state.balance}</b> </p>
                <div className="container pt-3">
                    {!this.state.cartHidden ?
                        <div className="row">
                            <div className="col-sm text-center">
                                <button
                                    hidden={this.state.hideDelButton}
                                    className="btn btn-outline-danger"
                                    onClick={this.deleteCartItem}
                                >
                                    Hapus Cart
                                </button>
                            </div>
                        </div>
                        : null}
                    <div className="row mt-3">
                        {!this.state.cartHidden ? (
                            <div className="col-sm">
                                <List
                                    title="My Cart"
                                    items={this.state.cartItems}
                                    onItemClick={this.handleDeleteItemFromCart}
                                    isShopList={false}
                                ></List>
                            </div>
                        ) : <div className="col-sm">
                            <List
                                title="List Items"
                                items={this.state.shopItems}
                                onItemClick={this.handleAddItemToCart}
                                isShopList={true}
                            ></List>
                        </div>}
                    </div>
                </div>
            </div>
        );
    }
}