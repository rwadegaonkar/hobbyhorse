<?php
/**
 * Created by JetBrains PhpStorm.
 * User: radhika
 * Date: 4/7/12
 * Time: 6:23 PM
 * To change this template use File | Settings | File Templates.
 */

require_once('Platform/Data/Users.php');
require_once('Platform/Data/Lessons.php');
require_once('Platform/Data/LessonTypes.php');

/**
 *
 */
Class Platform_Data {

    /**
     * @return
     */
    public $_data = array();


    public function __construct($data,$map='')
    {
        if(is_array($data))
        {
            foreach($data as $val)
            {
                if(is_object($val))
                {
                    $className = 'Platform_Data_' . ucfirst($map) . "_" . trim(ucfirst($map),'s');
                    $this->_data[] = $this->getResponseObject($val,new $className);
                }
            }
            return;
        }

        foreach($data as $key=>$val)
        {

            if(is_object($val))
            {
                $className = 'Platform_Data_' . ucfirst($key);
                try{
                    $this->_data[] = new $className($data->{$key}->{$key},$key); //@todo: double check this.
                }
                catch(Exception $e){
                    echo('Error :'.$e->getMessage());
                }
            }

        }
    }

    /**
     * @param
     * @return
     * @throws
     */

    public function getResponseObject($data = null, $obj)
    {
        if(!is_object($obj))
            throw new Exception("Error: data - {$obj} is not the object");

        foreach($data as $key=>$val)
        {
            $obj->$key = $val;
        }
        return $obj;
    }


    /**
     * @param
     * @return
     * @throws
     */

    public static function getDataObject($data = null)
    {
        if(!is_object($data))
            throw new Exception("Error: data - {$data} is not the object");

        return new self($data);
    }


    public function getProperty($objectName)
    {
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
        return false;
    }

    /**
     * @return
     */
    public function getData()
    {
        return $this->_data;
    }

    public function __get($name)
    {

        $method = 'get' . ucfirst($name);
        if(method_exists($this,$method))
        {
            return call_user_func(array(&$this, $method));
        }
        $objectName = "Platform_Data_". ucfirst($name);
        $value      = $this->getProperty($objectName);
        if($value == false)
            $objectName = 'Platform_Data_' . ucfirst($name) . "s_" . ucfirst($name);

        $value      = $this->getProperty($objectName);
        if($value == false)
            throw new exception("Error: No class exists with the name '{$objectName}");

        return $value;

    }

    public function __set($name = '',$val)
    {
        $this->_data[] = $val;
    }

}
