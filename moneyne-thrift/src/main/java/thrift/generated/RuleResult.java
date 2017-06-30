/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package thrift.generated;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2017-06-30")
public class RuleResult implements org.apache.thrift.TBase<RuleResult, RuleResult._Fields>, java.io.Serializable, Cloneable, Comparable<RuleResult> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("RuleResult");

  private static final org.apache.thrift.protocol.TField RULE_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("ruleName", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField RESULT_FIELD_DESC = new org.apache.thrift.protocol.TField("result", org.apache.thrift.protocol.TType.I32, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new RuleResultStandardSchemeFactory());
    schemes.put(TupleScheme.class, new RuleResultTupleSchemeFactory());
  }

  public String ruleName; // required
  /**
   * 
   * @see Result
   */
  public Result result; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    RULE_NAME((short)1, "ruleName"),
    /**
     * 
     * @see Result
     */
    RESULT((short)2, "result");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // RULE_NAME
          return RULE_NAME;
        case 2: // RESULT
          return RESULT;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.RULE_NAME, new org.apache.thrift.meta_data.FieldMetaData("ruleName", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.RESULT, new org.apache.thrift.meta_data.FieldMetaData("result", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, Result.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(RuleResult.class, metaDataMap);
  }

  public RuleResult() {
  }

  public RuleResult(
    String ruleName,
    Result result)
  {
    this();
    this.ruleName = ruleName;
    this.result = result;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public RuleResult(RuleResult other) {
    if (other.isSetRuleName()) {
      this.ruleName = other.ruleName;
    }
    if (other.isSetResult()) {
      this.result = other.result;
    }
  }

  public RuleResult deepCopy() {
    return new RuleResult(this);
  }

  @Override
  public void clear() {
    this.ruleName = null;
    this.result = null;
  }

  public String getRuleName() {
    return this.ruleName;
  }

  public RuleResult setRuleName(String ruleName) {
    this.ruleName = ruleName;
    return this;
  }

  public void unsetRuleName() {
    this.ruleName = null;
  }

  /** Returns true if field ruleName is set (has been assigned a value) and false otherwise */
  public boolean isSetRuleName() {
    return this.ruleName != null;
  }

  public void setRuleNameIsSet(boolean value) {
    if (!value) {
      this.ruleName = null;
    }
  }

  /**
   * 
   * @see Result
   */
  public Result getResult() {
    return this.result;
  }

  /**
   * 
   * @see Result
   */
  public RuleResult setResult(Result result) {
    this.result = result;
    return this;
  }

  public void unsetResult() {
    this.result = null;
  }

  /** Returns true if field result is set (has been assigned a value) and false otherwise */
  public boolean isSetResult() {
    return this.result != null;
  }

  public void setResultIsSet(boolean value) {
    if (!value) {
      this.result = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case RULE_NAME:
      if (value == null) {
        unsetRuleName();
      } else {
        setRuleName((String)value);
      }
      break;

    case RESULT:
      if (value == null) {
        unsetResult();
      } else {
        setResult((Result)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case RULE_NAME:
      return getRuleName();

    case RESULT:
      return getResult();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case RULE_NAME:
      return isSetRuleName();
    case RESULT:
      return isSetResult();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof RuleResult)
      return this.equals((RuleResult)that);
    return false;
  }

  public boolean equals(RuleResult that) {
    if (that == null)
      return false;

    boolean this_present_ruleName = true && this.isSetRuleName();
    boolean that_present_ruleName = true && that.isSetRuleName();
    if (this_present_ruleName || that_present_ruleName) {
      if (!(this_present_ruleName && that_present_ruleName))
        return false;
      if (!this.ruleName.equals(that.ruleName))
        return false;
    }

    boolean this_present_result = true && this.isSetResult();
    boolean that_present_result = true && that.isSetResult();
    if (this_present_result || that_present_result) {
      if (!(this_present_result && that_present_result))
        return false;
      if (!this.result.equals(that.result))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_ruleName = true && (isSetRuleName());
    list.add(present_ruleName);
    if (present_ruleName)
      list.add(ruleName);

    boolean present_result = true && (isSetResult());
    list.add(present_result);
    if (present_result)
      list.add(result.getValue());

    return list.hashCode();
  }

  @Override
  public int compareTo(RuleResult other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetRuleName()).compareTo(other.isSetRuleName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRuleName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.ruleName, other.ruleName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetResult()).compareTo(other.isSetResult());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetResult()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.result, other.result);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("RuleResult(");
    boolean first = true;

    sb.append("ruleName:");
    if (this.ruleName == null) {
      sb.append("null");
    } else {
      sb.append(this.ruleName);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("result:");
    if (this.result == null) {
      sb.append("null");
    } else {
      sb.append(this.result);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class RuleResultStandardSchemeFactory implements SchemeFactory {
    public RuleResultStandardScheme getScheme() {
      return new RuleResultStandardScheme();
    }
  }

  private static class RuleResultStandardScheme extends StandardScheme<RuleResult> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, RuleResult struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // RULE_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.ruleName = iprot.readString();
              struct.setRuleNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // RESULT
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.result = thrift.generated.Result.findByValue(iprot.readI32());
              struct.setResultIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, RuleResult struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.ruleName != null) {
        oprot.writeFieldBegin(RULE_NAME_FIELD_DESC);
        oprot.writeString(struct.ruleName);
        oprot.writeFieldEnd();
      }
      if (struct.result != null) {
        oprot.writeFieldBegin(RESULT_FIELD_DESC);
        oprot.writeI32(struct.result.getValue());
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class RuleResultTupleSchemeFactory implements SchemeFactory {
    public RuleResultTupleScheme getScheme() {
      return new RuleResultTupleScheme();
    }
  }

  private static class RuleResultTupleScheme extends TupleScheme<RuleResult> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, RuleResult struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetRuleName()) {
        optionals.set(0);
      }
      if (struct.isSetResult()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetRuleName()) {
        oprot.writeString(struct.ruleName);
      }
      if (struct.isSetResult()) {
        oprot.writeI32(struct.result.getValue());
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, RuleResult struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.ruleName = iprot.readString();
        struct.setRuleNameIsSet(true);
      }
      if (incoming.get(1)) {
        struct.result = thrift.generated.Result.findByValue(iprot.readI32());
        struct.setResultIsSet(true);
      }
    }
  }

}

