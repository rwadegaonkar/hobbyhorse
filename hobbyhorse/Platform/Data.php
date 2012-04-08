<?php
/**
 * Created by JetBrains PhpStorm.
 * User: radhika
 * Date: 4/7/12
 * Time: 6:23 PM
 * To change this template use File | Settings | File Templates.
 */

require_once('Platform/Data/Users.php');

/**
 *
 */
Class Platform_Data {

    /**
     * @return
     */
    protected $_data = array();


    public function __construct($data)
    {
        if(isset($data->users))
        {
            $this->_data[] = new Platform_Data_Users($data->users->users);
        }
        else if(isset($data->lessons))
        {

        }
        else
        {
            throw new Exception("Error: Wrong response");

        }

    }

    /**
     * @param Zend_Http_Response $response
     * @return json string
     * @throws
     */

    public static function getDataObject($data = null)
    {
        if(!is_object($data))
            throw new Exception("Error: data - {$data} is not the object");

        return new self($data);
    }



    public function __get($name)
    {
        $objectName = "Platform_Data_". ucfirst($name);
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

    public function __set($name,$val)
    {
        $this->_data[$name] = $val;
    }

}
