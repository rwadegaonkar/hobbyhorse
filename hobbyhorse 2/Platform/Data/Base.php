<?php
/**
 * Created by Radhika.
 * To change this template use File | Settings | File Templates.
 */
class Platform_Data_Base
{

    /**
     * @var
     */
    protected $_id;

    /**
     * @var
     */
    protected $_name;

    /**
     * @var
     */
    protected $_description;

    /**
     * @var
     */
    protected $_isDeleted;

    /**
     * @var
     */
    protected $_createdBy;

    /**
     * @var
     */
    protected $_lastUpdatedBy;

    /**
     * @var
     */
    protected $_createDate;

    /**
     * @var
     */
    protected $_lastUpdateDate;


    /**
     * @return
     */
    public function getName()
    {
        return $this->_name;
    }

    /**
     * @param  $name
     */
    public function setName($name)
    {
        $this->_name = $name;
    }

    /**
     * @return
     */
    public function getIsDeleted()
    {
        return $this->_isDeleted;
    }

    /**
     * @param  $isDeleted
     */
    public function setIsDeleted($isDeleted)
    {
        $this->_isDeleted = $isDeleted;
    }

    /**
     * @return
     */
    public function getId()
    {
        return $this->_id;
    }

    /**
     * @param  $id
     */
    public function setId($id)
    {
        $this->_id = $id;
    }

    /**
     * @param $createDate
     */
    public function setCreateDate($createDate)
    {
        $this->_createDate = $createDate;
    }

    /**
     * @return
     */
    public function getCreateDate()
    {
        return $this->_createDate;
    }

    /**
     * @param $createdBy
     */
    public function setCreatedBy($createdBy)
    {
        $this->_createdBy = $createdBy;
    }

    /**
     * @return
     */
    public function getCreatedBy()
    {
        return $this->_createdBy;
    }

    /**
     * @param  $desc
     */
    public function setDesc($desc)
    {
        $this->_desc = $desc;
    }

    /**
     * @return
     */
    public function getDesc()
    {
        return $this->_desc;
    }

    /**
     * @param $lastUpdateDate
     */
    public function setLastUpdateDate($lastUpdateDate)
    {
        $this->_lastUpdateDate = $lastUpdateDate;
    }

    /**
     * @return
     */
    public function getLastUpdateDate()
    {
        return $this->_lastUpdateDate;
    }

    /**
     * @param $lastUpdatedBy
     */
    public function setLastUpdatedBy($lastUpdatedBy)
    {
        $this->_lastUpdatedBy = $lastUpdatedBy;
    }

    /**
     * @return
     */
    public function getLastUpdatedBy()
    {
        return $this->_lastUpdatedBy;
    }

    /**
     * @param $name
     */
    public function __get($name)
    {
        $method = 'get' . ucfirst($name);
        if(method_exists($this,$method))
        {
            return call_user_func(array(&$this, $method));
        }
        else
            throw exception('Call to undefined property %s',$name);
    }

    public function __set($name,$value)
    {
        $method = 'set' . ucfirst($name);
        if(method_exists($this,$method))
        {
            return call_user_func(array(&$this,$method),$value);
        }
    }
}
