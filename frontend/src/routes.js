import React from 'react';
import { Route, Switch, Redirect } from 'react-router-dom';
import Home from './views/Home';
import HomeFunc from './views/HomeFunc';


export const AppRoutes = () => {
    return (
        <div>
            <Switch>
                <Route exact path="/class-based" component={Home} />
                <Route exact path="/func-based" component={HomeFunc} />
                <Route exact path="/">
                    <Redirect to="/class-based" />
                </Route>
            </Switch>
        </div>
    );
};