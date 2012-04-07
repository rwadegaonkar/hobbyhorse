<?php
/**
 * User: radhika\
 * To change this template use File | Settings | File Templates.
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
require_once("Platform/Data/Base.php");
/**
 *
 */
Class Platform_Data_Users {

    /**
     * @return
     */
    public $_data = array();


    public function __construct()
    {

    }

    public function __get($name)
    {

    }

    public function __set($name,$val)
    {
        $this->_data[$name] = $val;
    }
}
