<?php
/**
 * Created by JetBrains PhpStorm.
 * User: radhika
 * Date: 4/3/12
 * Time: 12:46 AM
 * To change this template use File | Settings | File Templates.
 */
require_once("Platform/Data/Base.php");

class Platform_Data_Users_User extends Platform_Data_Base
{
    protected $_username;
    /**
     * @var
     */
    protected $_email;
    /**
     * @var
     */
    protected $_skills;
    /**
     * @var
     */
    protected $_hobbies;
    /**
     * @var
     */
    protected $_location;

    /**
     * @return
     */

    public function __construct()
    {

    }


    public function getUsername()
    {
        return $this->_username;
    }

    /**
     * @param  $username
     */
    public function setUsername($username)
    {
        $this->_username = $username;
    }

    /**
     * @return
     */
    public function getEmail()
    {
        return $this->_email;
    }

    /**
     * @param  $email
     */
    public function setEmail($email)
    {
        $this->_email = $email;
    }

    /**
     * @return
     */
    public function getSkills()
    {
        return $this->_skills;
    }

    /**
     * @param  $skills
     */
    public function setSkills($skills)
    {
        $this->_skills = $skills;
    }

    /**
     * @return
     */
    public function getHobbies()
    {
        return $this->_hobbies;
    }

    /**
     * @param  $hobbies
     */
    public function setHobbies($hobbies)
    {
        $this->_hobbies = $hobbies;
    }

    /**
     * @return
     */
    public function getLocation()
    {
        return $this->_location;
    }

    /**
     * @param  $location
     */
    public function setLocation($location)
    {
        $this->_location = $location;
    }
}
