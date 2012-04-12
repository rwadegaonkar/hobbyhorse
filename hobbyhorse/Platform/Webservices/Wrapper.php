<?php

/**
 * Created by JetBrains PhpStorm.
 * User: radhika
 * Date: 3/18/12
 * Time: 4:58 PM
 * To change this template use File | Settings | File Templates.
 */
require_once('Zend/Http/Client.php');

class Platform_Webservices_Wrapper {

    /**
     * @var string
     */
    protected $_host;

    /**
     * @var string
     */
    protected $_port;

    /**
     * @var string
     */
    protected $_responseDataType;

    /**
     * @var
     */
    protected $_client;

    /**
     *
     */
    public function __construct() {
        $this->_responseDataType = ".json";
        $this->_host = "http://localhost";
        $this->_port = "8080";
        $this->_domain = "hobbyhorse";
    }

    public function request($request_string = '', $method = 'get', $data = null,$responseType = '') {
        if ($request_string == '')
            throw new Exception("Error: Request string cannot be empty");

        $config = array(
            'adapter' => 'Zend_Http_Client_Adapter_Socket',
            'ssltransport' => 'tls',
            'timeout'      => 30
        );

        if(!is_null($data) && $responseType == '') 
            $this->_responseDataType="";
                                              
        $this->_client = new Zend_Http_Client($this->_host . ":" . 
                                              $this->_port . "/" . 
                                              $this->_domain . "/{$request_string}" . 
                                              $this->_responseDataType, $config);
            
      
        if(!is_null(($data)))
        {
            if(is_string($data))
                $this->_client->setRawData($data, 'application/json');
            else if(is_array($data))
                $this->_client->setParameter{ucfirst($method)}((array) $data);
        }

        try {
            $response = $this->_client->request(strtoupper($method));
            $jsonData = $this->parseZendResponse($response);
            return json_decode($jsonData);
        } catch (Zend_Http_Client_Adapter_Exception $e) {
            echo('Request to webservice failed :' . $e->getMessage());
        }
    }


    /**
     * @param Zend_Http_Response $response
     * @return json string
     * @throws
     */
    public function parseZendResponse(Zend_Http_Response $response) {
        if ($response->getStatus() == 200) {
            return $response->getBody();
        } else {
            throw new Exception("Error: Status is: " . $response->getStatus() . " message: " . $response->getMessage());
        }
    }

}
