<?php
/**
 * User: radhika\
 * To change this template use File | Settings | File Templates.
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

require_once('Platform/Data.php');
require_once('Platform/Data/Users/User.php');

/**
 *
 */
Class Platform_Data_Users extends Platform_Data{

    /**
     * @return
     */
    public function __construct($users = null)
    {
        if(isset($users) && is_array($users))
        {
            foreach($users as $user)
            {
                $this->_data[] = $this->getUserObject($user);
            }
        }
    }

    public function getUserObject($user)
    {
        $userObj = new Platform_Data_Users_User();
        foreach($user as $key=>$val)
        {
            $userObj->$key = $val;
        }
        return $userObj;
    }

    public function __get($name)
    {
        $objectName = "Platform_Data_Users_". ucfirst($name);
        foreach($this->_data as $data)
        {
            if(is_object($data))
            {
                if(get_class($data) == $objectName)
                {
                    return $data;
                }
            }
        }
    }

}
