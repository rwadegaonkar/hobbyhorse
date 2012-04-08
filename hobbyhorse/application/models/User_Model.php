<?php
/**
 * Created by JetBrains PhpStorm.
 * User: radhika
 * Date: 3/18/12
 * Time: 4:27 PM
 * To change this template use File | Settings | File Templates.
 */

require_once('Platform/Webservices/Wrapper.php');
require_once('Platform/Data.php');

class User_Model extends CI_Model
{
    public function __construct()
    {
        parent::__construct();
    }

    public function getUsers()
    {
        $myWrapper = new Platform_Webservices_Wrapper();
        $jsonObj =  $myWrapper->request('users/');
        return  Platform_Data::getDataObject($jsonObj);
    }
}
